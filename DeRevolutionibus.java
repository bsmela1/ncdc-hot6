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

    public Planet(String name, float cycleTime, float orbitRay, float x, float y){
        this.name = name;
        this.cycleTime = cycleTime;
        this.orbitRay = orbitRay;
        this.x = x;
        this.y = y;
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
}

public class DeRevolutionibus {
    //////////////////
    //      FUNCTIONS
    //      ||  ||  ||
    //      \/  \/  \/
    static Planet[] reduceArray(Planet[] array, int id){
        Planet[] newArray = new Planet[array.length];
        for (int i = 0; i < newArray.length; i++) {
            if(i!=id) newArray[i] = array[i];
            else newArray[i] = null;
        }
        return newArray;
    }

    static void calcPlanetsPositions(Planet[] optPlanetsArray, float day) {
        for (int i = 0; i < optPlanetsArray.length; i++) {
            optPlanetsArray[i].setPlanetPos(optPlanetsArray[i].getX(), optPlanetsArray[i].getY());
        }
    }

    static void printOutputPlanets(Planet[] array){
        for (int n = array.length; n > 1; n--) {
            for (int i = 0; i < array.length-1; i++) {
                if(array[i].getX() < array[i+1].getX()){
                    Planet tempPlanet = array[i];
                    array[i] = array[i+1];
                    array[i+1] = tempPlanet;
                }
            }
        }

        for (int i = 0; i < array.length-1; i++) {
            DecimalFormat df = new DecimalFormat("0.000");
            float roundedI = Float.parseFloat(df.format(array[i].getX()));
            float roundedI2 = 0f;

            if(array[i+1]==null){
                for (int j = 0; j < array.length; j++) {
                    if(array[j]!=null){
                        if (j == (array.length-2)) System.out.print(array[j].getName());
                        else System.out.print(array[j].getName() + ", ");
                    }
                }
            }else{
                roundedI2 = Float.parseFloat(df.format(array[i+1].getX()));
            }

            if(Math.abs(roundedI-roundedI2)<0.005){
                if(array[i].getY()>array[i+1].getY()){
                    array = reduceArray(array, i);
                    for (int g = array.length; g > 1; g--) {
                        for (int l = 0; l < array.length-1; l++) {
                            if(array[l] == null){
                                array[l] = array[l+1];
                                array[l+1] = null;
                            }
                        }
                    }
                }else{
                    array = reduceArray(array, i+1);
                    for (int g = array.length; g > 1; g--) {
                        for (int l = 0; l < array.length-1; l++) {
                            if(array[l] == null){
                                array[l] = array[l+1];
                                array[l+1] = null;
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ///////////////////
        //      VARIABLES
        //      ||  ||  ||
        //      \/  \/  \/
        Scanner scanner = new Scanner(System.in);
        final int maxPlanetsCount = 100000;
        final int minPlanetsCount = 2;
        final int maxOrbitRay = 100000;
        final int maxCycleTime = 200000;
        int planetsCount = 0;
        float day = Float.parseFloat(args[0]);
        float currentDay = 0f;
        Planet [] planetsArray = new Planet [maxPlanetsCount];

        for (int i = 0; scanner.hasNext(); i++) {
            String line = scanner.nextLine();
            String[] planetComps = line.split(",");
            String planetName = planetComps[0];
            float planetTime = Float.parseFloat(planetComps[1]);
            float planetRay = Float.parseFloat(planetComps[2]);
            planetsArray[i] = new Planet(planetName, planetTime, planetRay, planetRay*(-1), 0);
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

        double speed = (2 * Math.PI * optPlanetsArray[0].getOrbitRay())/planetsArray[0].getCycleTime();
        while(currentDay<day){
            calcPlanetsPositions(optPlanetsArray, currentDay);
            currentDay+=0.01f;
        }

        printOutputPlanets(optPlanetsArray);
        System.exit(0);
    }
}