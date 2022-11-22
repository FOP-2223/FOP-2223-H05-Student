package h05;

public class MessageHelper_H_2 {

    private static boolean firstCharIsVowel(String s) {
        return s.matches("^[AEIOU].*");
    }

    public static boolean matchesFormat(String s) {
        return s.matches("^I am (an [AEIOU]|a [BCDFGHJKLMNPQRSTVWXYZ])[a-z0-9 ]*\\.$");
    }

    private static String toNoun(String s) {
        String[] splits = s.split("", 2);
        return "%s%s".formatted(splits[0].toUpperCase(), splits[1].toLowerCase());
    }

    public static String expectedName(String name) {
        String transportTypeString = name == null ? "undefined" : toNoun(name);
        String indefiniteArticle = firstCharIsVowel(transportTypeString) ? "an" : "a";
        return "I am %s %s.".formatted(indefiniteArticle, transportTypeString);
    }
}
