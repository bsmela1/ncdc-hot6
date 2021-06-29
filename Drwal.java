import java.util.Scanner;

public class Drwal {
    static void colorPainting(int x, int y, char[][] inPainting, char color){
        if(inPainting[x][y]=='#') return;

        if(inPainting[x][y]==' ') inPainting[x][y]=color;

        if(x<inPainting.length-1){
            if(inPainting[x+1][y]==' '){
                colorPainting(x+1, y, inPainting, color);
            }
        }

        if(x>0) {
            if (inPainting[x - 1][y] == ' ' && (x - 1 > 0)) {
                colorPainting(x - 1, y, inPainting, color);
            }
        }

        if(y<inPainting[0].length-1){
            if(inPainting[x][y+1]==' ' && (y+1<inPainting[0].length)){
                colorPainting(x, y+1, inPainting, color);
            }
        }

        if(y>0){
            if(inPainting[x][y-1]==' ' && (y-1>0)){
                colorPainting(x, y-1, inPainting, color);
            }
        }
    }

    public static void main(String[] args){
        int yStart = Integer.parseInt(args[0])-1;
        int xStart = Integer.parseInt(args[1])-1;
        char color = args[2].charAt(0);
        int width = Integer.parseInt(args[3]);
        int height = Integer.parseInt(args[4]);

        if(width<0 || width>5000 || height<0 || height>5000 || xStart>width || yStart>height){
            System.out.println("klops");
            System.exit(0);
        }

        if(width==0 || height==0){
            System.exit(0);
        }

        Scanner scanner = new Scanner(System.in);
        char[][] painting = new char[height][width];

        for (int i = 0; scanner.hasNext() && i<height; i++) {
            String line = scanner.nextLine();
            int j=0;
            for (char ch: line.toCharArray()) {
                if(j<width){
                    painting[i][j] = ch;
                    j++;
                }
            }
        }
        colorPainting(xStart, yStart, painting, color);

        for (char[] s: painting) {
            System.out.println(s);
        }

        System.exit(0);
    }
}
