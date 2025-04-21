package battlesimulator.battlesimulator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class FormatMethods {

    public static String leftPad(String string, int padding) {
        String paddedString = String.format("%" + padding + "s", string);
        return paddedString;
    }

    public static String rightPad(String string, int padding) {
        String paddedString = String.format("%" + (-padding) + "s", string);
        return paddedString;
    }

    public static String getCorrectIndefiniteArticle(String string) {
        ArrayList<String> vowels = new ArrayList<>();
        vowels.add("a");
        vowels.add("e");
        vowels.add("i");
        vowels.add("o");
        vowels.add("u");
        vowels.add("å");
        vowels.add("ä");
        vowels.add("ö");

        String article = (vowels.contains(string.toLowerCase().substring(0,1)) ? "an" : "a");
        return article;
    }

    public static String getCorrectDefiniteArticle(Character character) {
        String article = (character instanceof ClassEnemy ? "The " : "");
        return article;
    }

    public static String pointsSingularPlural(int attackDamage) {
        String pointString = (attackDamage == 1 ? "point" : "points");
        return pointString;
    }

    public static String capitalize(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    public static String formatSetToString(HashSet<String> set) {
        int setSize = set.size();
        String returnString = "";
        Iterator<String> iterator = set.iterator();

        // Return empty string if set's size is 0
        if (setSize == 0) {
            return returnString;
        }

        // Return the only element if set's size is 1
        if (setSize == 1) {
            returnString = iterator.next();
            return returnString;
        }

        // Build a string if set's size is 2 or larger
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < setSize; i++) {
            String element = iterator.next();
            if (i == setSize - 1) {

                // Add 'and' before the last element
                builder.append("and ").append(element);
            } else if (i == setSize - 2) {

                // Append the element if it's the second last
                builder.append(element).append(" "); 
            } else {

                // Otherwise, add the element followed by a comma
                builder.append(element).append(", "); 
            }   
        }

        returnString = builder.toString();
        return returnString;
    }
}