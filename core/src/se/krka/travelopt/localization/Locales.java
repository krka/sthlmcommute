package se.krka.travelopt.localization;

public class Locales {
    public static TravelOptLocale getLocale(String locale) {
        if (locale.equals("sv")) {
            return SwedishLocale.INSTANCE;
        }
        return EnglishLocale.INSTANCE;
    }
}
