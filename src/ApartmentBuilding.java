import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ApartmentBuilding {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<String> inputLines = new ArrayList<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isEmpty()) break;
            inputLines.add(line);
        }

        String inputString = String.join("\n", inputLines);
        System.out.println(ApartmentBuilding(inputString));
    }

    public static int ApartmentBuilding(String inputString){

        String[] lines = inputString.split("\n");

        if (lines.length == 0) {
            return 0;
        }

        String[] firstLine = lines[0].split(" ");

        if (firstLine.length < 4) {
            return 0;
        }

        int numWind = Integer.parseInt(firstLine[2]) * Integer.parseInt(firstLine[3]);

        int rows = lines.length - 1;
        int cols = lines[1].length();

        char[][] matrix = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            String currentLine = lines[i + 1];
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = currentLine.charAt(j);
            }
        }

        int segmentSizeX = Integer.parseInt(firstLine[0]);
        int segmentSizeY = Integer.parseInt(firstLine[1]);

        int numX = 0;
        int result = 0;
        for(int i = 0; i < rows; i += segmentSizeX) {
            for(int j = 0; j < cols; j += segmentSizeY){

                for(int x = 0; x < segmentSizeX; x++) {
                    for(int y = 0; y < segmentSizeY; y++) {
                        if(i + x < rows && y + j < cols) {
                            if(matrix[i + x][y + j] == 'X') {
                                numX++;
                            }
                        }
                    }
                }

                if(numX >= (numWind + 1) / 2)
                    result++;

                numX = 0;
            }
        }

        return result;
    }

}