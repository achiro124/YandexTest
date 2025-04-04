import java.util.*;

public class Translate {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> inputLines = new ArrayList<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isEmpty()) break;
            inputLines.add(line);
        }

        System.out.println(translate(inputLines));
    }

    public static String translate(List<String> inputLines) {
        Map<String, String> dictionary = new HashMap<>();
        dictionary.put("2", "ABC");
        dictionary.put("3", "DEF");
        dictionary.put("4", "GHI");
        dictionary.put("5", "JKL");
        dictionary.put("6", "MNO");
        dictionary.put("7", "PQRS");
        dictionary.put("8", "TUV");
        dictionary.put("9", "WXYZ");

        String encryptedString = inputLines.get(0);
        int n = Integer.parseInt(inputLines.get(1));
        List<String> dictionaryList = inputLines.subList(2, 2 + n);

        if (encryptedString.isEmpty() || dictionaryList.isEmpty()) {
            throw new IllegalArgumentException("Пустые входные данные");
        }

        List<Map.Entry<String, String>> wordCodes = new ArrayList<>();
        for (String word : dictionaryList) {
            StringBuilder code = new StringBuilder();
            for (char c : word.toCharArray()) {
                String charStr = String.valueOf(c);
                for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                    if (entry.getValue().contains(charStr)) {
                        int pressCount = entry.getValue().indexOf(charStr) + 1;
                        code.append(entry.getKey().repeat(pressCount));
                        break;
                    }
                }
            }
            wordCodes.add(new AbstractMap.SimpleEntry<>(word, code.toString()));
        }

        wordCodes.sort((e1, e2) -> {
            int lengthCompare = Integer.compare(e2.getValue().length(), e1.getValue().length());
            return lengthCompare != 0 ? lengthCompare : e1.getKey().compareTo(e2.getKey());
        });

        List<String> result = new ArrayList<>();
        int currentPos = 0;
        int totalLength = encryptedString.length();
        int lastPos = -1;

        while (currentPos < totalLength) {

            if (currentPos == lastPos) {
                throw new RuntimeException("Зацикливание при расшифровке. Позиция: " + currentPos);
            }
            lastPos = currentPos;

            boolean found = false;
            for (Map.Entry<String, String> entry : wordCodes) {
                String wordCode = entry.getValue();
                int codeLength = wordCode.length();

                if (currentPos + codeLength <= totalLength) {
                    String currentSubstring = encryptedString.substring(currentPos, currentPos + codeLength);
                    if (currentSubstring.equals(wordCode)) {
                        result.add(entry.getKey());
                        currentPos += codeLength;
                        found = true;
                        break;
                    }
                }
            }

            if (!found) {
                throw new RuntimeException("Не удалось расшифровать сообщение");
            }
        }

        return String.join(" ", result);
    }
}