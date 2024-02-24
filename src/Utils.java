public class Utils {
    public static String removeFirstNWords(String string, int nWords) {
        StringBuilder finalString = new StringBuilder();
        String[] splittedString = string.split("[\\s\\t]+");

        for (int i = nWords; i < splittedString.length; i++) {
            finalString.append(splittedString[i]);
            if (i < splittedString.length - 1) {
                finalString.append(' ');
            }
        }
        return finalString.toString().trim();
    }
}