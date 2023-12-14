package at.wirthi.learningspring.nametable;

import at.wirthi.learningspring.util.Log;

import java.util.HashMap;
import java.util.Map;

public class NameTable {

    private static Map<String, String> stationMap;

    static {
        stationMap = new HashMap<>();

        //Linie 1, 2
        stationMap.put("Linz/Donau solarCity", "solarCity");
        stationMap.put("Linz/Donau Auwiesen", "Auwiesen");
        stationMap.put("Linz/Donau JKU I Universität", "JKU Universität");

        //linie 3, 4
        stationMap.put("Linz/Donau Landgutstraße", "Landgutstraße");
        stationMap.put("Traun OÖ Trauner Kreuzung", "Trauner Kreuzung");
        stationMap.put("Traun OÖ Schloss Traun", "Schloss Traun");
        stationMap.put("Leonding Im Bäckerfeld", "Im Bäckerfeld"); //Einrücker in die Remise
        stationMap.put("Leonding Doblerholz", "Doblerholz"); //Einrücker in die Remise

        //Linie 17
        stationMap.put("Linz/Donau Industriezeile", "Industriezeile");
        stationMap.put("Hitzing", "Hitzing"); //why no location?
        stationMap.put("Leonding Meixnerkreuzung", "Meixnerkreuzung");

        //Linie 18
        stationMap.put("Linz/Donau, Turmstraße", "Turmstraße");

        //Linie 19
        stationMap.put("Linz/Donau Pichling", "Pichling");
        stationMap.put("Linz/Donau Pichlinger See", "Pichlinger See");
        stationMap.put("Linz/Donau Fernheizkraftwerk", "Fernheizkraftwerk");

        //linie 26
        stationMap.put("Linz/Donau St. Margarethen", "Margarethen");
        stationMap.put("Linz/Donau Stadion", "Stadion");
        stationMap.put("Linz/Donau Taubenmarkt", "Taubenmarkt");

        //linie 27
        stationMap.put("Linz/Donau Chemiepark", "Chemiepark");
        stationMap.put("Linz/Donau Schiffswerft", "Schiffswerft");

        //linie 41
        stationMap.put("Linz/Donau Hessenplatz", "Hessenplatz");
        stationMap.put("Linz/Donau Baintwiese", "Baintwiese");

        //linie 43
        stationMap.put("Traun OÖ Stadtfriedhof Linz", "Stadtfriedhof");

        //linie 45, 46
        stationMap.put("Linz/Donau Froschberg", "Froschberg");
        stationMap.put("Linz/Donau Ziegeleistraße", "Ziegeleistraße");
        stationMap.put("Linz/Donau Hafen", "Hafen");
        stationMap.put("Linz/Donau Stieglbauernstraße", "Stieglbauernstraße");
        stationMap.put("Linz/Donau Hauptbahnhof", "Hauptbahnhof");

        //linie 107
        stationMap.put("Linz/Donau Hatschekstraße", "Hatschekstraße");

        //linie 191
        stationMap.put("Traun OÖ St. Martin", "St. Martin");
        stationMap.put("Leonding Gaumberg", "Gaumberg");

        //linie 192
        stationMap.put("Linz/Donau Theatergasse", "Theatergasse");
        stationMap.put("Leonding Stadtplatz Leonding", "Stadtplatz Leonding");
    }

    public static String getPublicName(String internalName) {
        String publicName = stationMap.get(internalName);
        if (publicName == null) {
            Log.warn("NameTable could not find: " + internalName);
            return internalName;
        }
        return publicName;
    }
}
