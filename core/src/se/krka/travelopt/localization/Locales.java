package se.krka.travelopt.localization;

public class Locales {
    public static TravelOptLocale getLocale(String locale) {
        if (locale.equals("sv")) {
            return new SwedishLocale();
        }
        return new EnglishLocale();
    }
}
