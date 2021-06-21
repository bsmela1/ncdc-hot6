//House of Talents #6 NCDC
//Autor: Bartosz Smela
//Zadanie 2: "Uproszczenia"

public class Uproszczenia {
    static public int pathLength = 0;

    //FUNCTIONS
    //   ||
    //   \/
    static void fillArea(char[][] area){
        for (int i = 0; i < area.length; i++) {
            for (int j = 0; j < area[0].length; j++) {
                area[i][j] = '*';
            }
        }
    }

    static void goRight(char[][] area, int x, int y, int n) {
        int newX = 0;
        if (n > 0) {
            for (int i = x; i < area[0].length - 1; i++) {
                area[y][i] = ' ';
                pathLength++;
                newX = i;
                if(area[0].length > i+2){
                    if(area[y][i+2]==' '){
                        break;
                    }
                }
            }
        }else return;
        if (x != 0) n -= 1;
        goDown(area, newX, y, n);
    }

    static void goUp(char[][] area, int x, int y, int n) {
        int newY = 0;
        if (n > 0) {
            for (int i = y; i > 0; i--) {
                area[i][x] = ' ';
                pathLength++;
                newY = i;
                if(0 <= i-2){
                    if(area[i-2][x]==' '){
                        break;
                    }
                }
            }
        }else return;
        if (y != area.length-1) n -= 1;
        goRight(area, x, newY, n);
    }

    static void goLeft(char[][] area, int x, int y, int n) {
        int newX = 0;
        if (n > 0) {
            for (int i = x; i > 0; i--) {
                area[y][i] = ' ';
                pathLength++;
                newX = i;
                if(0 <= i-2){
                    if(area[y][i-2]==' '){
                        break;
                    }
                }
            }
        }else return;
        if (x != area[0].length-1) n -= 1;
        goUp(area, newX, y, n);
    }

    static void goDown(char[][] area, int x, int y, int n){
        int newY = 0;
        if(n > 0){
            for (int i = y; i < area.length-1; i++) {
                area[i][x] = ' ';
                pathLength++;
                newY = i;
                if(area.length > i+2){
                    if(area[i+2][x]==' '){
                        break;
                    }
                }
            }
        }else return;
        if(y!=0) n-=1;
        goLeft(area, x, newY, n);
    }

    static void drawPath(char[][] area, char direction, int n){
        switch (direction){
            case 'N':
                goDown(area, area[0].length-2, 0, n);
                break;
            case 'S':
                goUp(area, 1, area.length-1, n);
                break;
            case 'E':
                goLeft(area, area[0].length-1, area.length-2, n);
                break;
            case 'W':
                goRight(area, 0, 1, n);
                break;
        }
    }

    static void printArea(char[][] area, int n){
        for (int i = 0; i < area.length; i++) {
            for (int j = 0; j < area[0].length; j++) {
                System.out.print(area[i][j]);
                if (j==area[0].length-1) System.out.print('\n');
            }
        }
        System.out.println(pathLength-n);
    }

    public static void main(String[] args) {
        //VARIABLES
        //   ||
        //   \/
        String input = args[0];
        char[] inputArray = input.toCharArray();
        int speedMultiplier = Integer.parseInt(Character.toString(inputArray[0]));
        char direction = inputArray[1];
        int width = 0;
        int height = 0;

        if(direction=='N' || direction=='S'){
            width=3+speedMultiplier;
            height=2+speedMultiplier;
        }else{
            width=2+speedMultiplier;
            height=3+speedMultiplier;
        }

        char[][] area = new char[height][width];
        fillArea(area);
        drawPath(area, direction, speedMultiplier);
        printArea(area, speedMultiplier);
    }
}
