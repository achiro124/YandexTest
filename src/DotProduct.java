import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DotProduct {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<String> inputLines = new ArrayList<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isEmpty()) break;
            inputLines.add(line);
        }

        String inputString = String.join("\n", inputLines);
        System.out.println(dotProduct(inputString));

    }

    public static BigInteger dotProduct(String inputString){

        String[] inputStringLines = inputString.split("\n");

        int N = Integer.parseInt(inputStringLines[0]);
        List<BigInteger> VectorQ = new ArrayList<>();
        List<BigInteger> VectorC = new ArrayList<>();

        for(String line: inputStringLines[1].split(" ")){
            VectorQ.add(BigInteger.valueOf(Long.parseLong(line)));
        }

        for(String line: inputStringLines[2].split(" ")){
            VectorC.add(BigInteger.valueOf(Long.parseLong(line)));
        }

        int A = Integer.parseInt(inputStringLines[3].split(" ")[0]);
        int B = Integer.parseInt(inputStringLines[3].split(" ")[1]);

        List<BigInteger> VectorD = new ArrayList<>();

        for(BigInteger c : VectorC){
            long d = Math.round((c.longValue() * (B - A)) / 255.0) + A;
            VectorD.add(BigInteger.valueOf(d));
        }

        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < N; i++) {
            BigInteger product = VectorQ.get(i).multiply(VectorD.get(i));
            result = result.add(product);
        }

        return result;
    }

}
