import java.util.*;

public class ProcessTaxiOrders {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> inputLines = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) break;
            inputLines.add(line);
        }

        List<String> results = processTaxiOrders(inputLines);

        for (String result : results) {
            System.out.println(result);
        }
    }

    public static List<String> processTaxiOrders(List<String> inputLines) {

        List<String> result = new ArrayList<>();

        if (inputLines.isEmpty()) {
            return result;
        }

        String[] firstLine = inputLines.get(0).split(" ");
        int N = Integer.parseInt(firstLine[0]);
        int L = Integer.parseInt(firstLine[1]);
        int S = Integer.parseInt(firstLine[2]);

        Map<Integer, Integer> lastKnownPosition = new HashMap<>();
        Map<Integer, Integer> lastUpdateTime = new HashMap<>();

        for (int i = 1; i < inputLines.size(); i++) {
            String[] parts = inputLines.get(i).split(" ");
            String eventType = parts[0];
            int timestamp = Integer.parseInt(parts[1]);

            if (eventType.equals("TAXI")) {
                int taxiId = Integer.parseInt(parts[2]);
                int position = Integer.parseInt(parts[3]);

                lastKnownPosition.put(taxiId, position);
                lastUpdateTime.put(taxiId, timestamp);
            }
            else if (eventType.equals("ORDER")) {
                int orderId = Integer.parseInt(parts[2]);
                int orderPosition = Integer.parseInt(parts[3]);
                int orderTime = Integer.parseInt(parts[4]);

                List<Integer> availableTaxis = new ArrayList<>();

                for (int taxiId : lastKnownPosition.keySet()) {
                    int lastPos = lastKnownPosition.get(taxiId);
                    int lastTime = lastUpdateTime.get(taxiId);
                    int timeDiff = timestamp - lastTime;

                    int maxDistance = S * timeDiff;

                    int distance;
                    if (lastPos <= orderPosition) {
                        distance = orderPosition - lastPos;
                    } else {
                        distance = L - lastPos + orderPosition;
                    }

                    int minDistance = Math.max(0, distance - maxDistance);

                    int minTime = minDistance / S;
                    if (minDistance % S != 0) {
                        minTime += 1;
                    }

                    if (minTime <= orderTime) {
                        availableTaxis.add(taxiId);
                    }
                }

                if (availableTaxis.isEmpty()) {
                    result.add("-1");
                } else {
                    Set<Integer> uniqueTaxis = new LinkedHashSet<>(availableTaxis);
                    List<Integer> resultList = new ArrayList<>(uniqueTaxis);
                    if (resultList.size() > 5) {
                        resultList = resultList.subList(0, 5);
                    }

                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < resultList.size(); j++) {
                        if (j > 0) sb.append(" ");
                        sb.append(resultList.get(j));
                    }
                    result.add(sb.toString());
                }
            }
        }

        return result;
    }
}