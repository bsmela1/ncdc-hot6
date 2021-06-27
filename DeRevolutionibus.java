//House of Talents #6 NCDC
//Autor: Bartosz Smela
//Zadanie 1: "O obrotach sfer niebieskich"

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

class Planet{
    private String name;
    private float cycleTime;
    private float orbitRay;
    private float x;
    private float y;
    private float speed;

    public Planet(String name, float cycleTime, float orbitRay, float x, float y, float speed){
        this.name = name;
        this.cycleTime = cycleTime;
        this.orbitRay = orbitRay;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void setPlanetPos(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }

    public String getName(){
        return this.name;
    }

    public float getOrbitRay(){
        return this.orbitRay;
    }

    public float getCycleTime(){
        return this.cycleTime;
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }

    public float getSpeed(){
        return this.speed;
    }
}

public class DeRevolutionibus {
    //////////////////
    //      FUNCTIONS
    //      ||  ||  ||
    //      \/  \/  \/
    static void calcPlanetsPositions(Planet[] planetsArray, float day) {
        if(day>0){
            for (int i = 0; i < planetsArray.length; i++) {
                double angle = planetsArray[i].getSpeed() * day;
                float cosA = (float)Math.cos(angle%(2*Math.PI)); //stosowanie modulo nie jest konieczne,
                // lecz zrobilem to zeby byl kąt do 360 stopni, akurat tu jest zamieniony na radiany
                float sinA = (float)Math.sin(angle%(2*Math.PI));

                int multiplier = 0;
                while(angle-(2*Math.PI)>0){
                    angle-=(2*Math.PI);
                    multiplier++;
                }

                float x = planetsArray[i].getOrbitRay()*cosA*(-1);
                float y = planetsArray[i].getOrbitRay()*sinA*(-1);
                planetsArray[i].setPlanetPos(x, y);
            }
        }else return;
    }

    static Planet[] reduceArray(Planet[] array, int id){
        Planet[] newArray = new Planet[array.length];
        for (int i = 0; i < newArray.length; i++) {
            if(i!=id) newArray[i] = array[i];
            else newArray[i] = null;
        }
        return newArray;
    }

    static void printArray(Planet[] array){
        for (int j = 0; j < array.length; j++) {
            if(array[j]!=null){
                if (j+1 <= array.length-1){
                    if (array[j+1] == null) {
                        System.out.print(array[j].getName());
                        return;
                    }else System.out.print(array[j].getName() + ", ");
                }else System.out.print(array[j].getName());
            }
        }
    }

    static void setNullToEnd(Planet[] array){
        for (int g = array.length; g > 1; g--) {
            for (int l = 0; l < array.length-1; l++) {
                if(array[l] == null){
                    array[l] = array[l+1];
                    array[l+1] = null;
                }
            }
        }
    }

    static void printOutputPlanets(Planet[] array){
        for (int n = array.length; n > 1; n--) {
            for (int i = 0; i < array.length-1; i++) {
                if(array[i].getX() > array[i+1].getX()){
                    Planet tempPlanet = array[i];
                    array[i] = array[i+1];
                    array[i+1] = tempPlanet;
                }
            }
        }

        int i = 0;
        while(i < array.length-1){
            DecimalFormat df = new DecimalFormat("0.000");
            float roundedI = 0f;
            float roundedI2 = 0f;

            if(array[i]==null || array[i+1]==null){
                printArray(array);
                return;
            }else{
                roundedI = Float.parseFloat(df.format(array[i].getX()));
                roundedI2 = Float.parseFloat(df.format(array[i+1].getX()));
            }

            if(Math.abs(roundedI-roundedI2)<0.005){
                if(array[i].getY()>array[i+1].getY()){
                    array = reduceArray(array, i);
                    setNullToEnd(array);
                }else{
                    array = reduceArray(array, i+1);
                    setNullToEnd(array);
                }
                i=0;
            }else{
                if(i==array.length-2) {
                    printArray(array);
                    return;
                }
                i++;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ///////////////////
        //      VARIABLES
        //      ||  ||  ||
        //      \/  \/  \/
        final int maxPlanetsCount = 100000;
        final int minPlanetsCount = 2;
        final int maxOrbitRay = 100000;
        final int maxCycleTime = 200000;
        float day = Float.parseFloat(args[0]);
        int planetsCount = 0;
        Planet [] planetsArray = new Planet [maxPlanetsCount];
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; scanner.hasNext(); i++) {
            String line = scanner.nextLine();
            String[] planetComps = line.split(",");
            String planetName = planetComps[0];
            float planetTime = Float.parseFloat(planetComps[1]);
            float planetRay = Float.parseFloat(planetComps[2]);

            float sp = 0f;
            if(planetTime>0) sp=(2*(float)Math.PI)/planetTime;
            else sp=0;

            planetsArray[i] = new Planet(planetName, planetTime, planetRay, planetRay*(-1), 0, sp);
            if(planetsArray[i].getCycleTime() > maxCycleTime || planetsArray[i].getOrbitRay() > maxOrbitRay){
                System.out.println("klops");
                System.exit(0);
            }
            planetsCount = i+1;
        }

        if(planetsCount<minPlanetsCount || planetsCount>maxPlanetsCount){
            System.out.println("klops");
            System.exit(0);
        }

        Planet[] optPlanetsArray = new Planet[planetsCount];
        for (int j = 0; j < planetsCount; j++) {
            optPlanetsArray[j] = planetsArray[j];
        }

        calcPlanetsPositions(optPlanetsArray, day);
        printOutputPlanets(optPlanetsArray);
        System.exit(0);
    }
}
