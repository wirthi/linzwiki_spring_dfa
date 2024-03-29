package at.wirthi.learningspring.nametable;

import at.wirthi.learningspring.util.Log;

import java.util.HashMap;
import java.util.Map;

public class NameTable {

    private static Map<String, String> stationMap;

    static {
        stationMap = new HashMap<>();

        //random
        stationMap.put("Linz/Donau Fadingerstraße", "Fadingerstraße");
        stationMap.put("Linz/Donau Seidelbastweg", "Seidelbastweg");
        stationMap.put("Rohrbach b.Linz Wambacherberg", "Wambacherberg");
        stationMap.put("Linz/Donau Unionkreuzung", "Unionkreuzung");
        stationMap.put("Linz/Donau Kudlichstraße", "Kudlichstraße");
        stationMap.put("Steyregg Plesching", "Plensching");
        stationMap.put("Linz/Donau Neue Heimat", "Neue Heimat");
        stationMap.put("Linz/Donau Neue Welt", "Neue Welt");
        stationMap.put("Linz/Donau Prinz-Eugen-Straße", "Prinz-Eugen-Straße");
        stationMap.put("Leonding Remise Leonding", "Remise Leonding");
        stationMap.put("Linz/Donau Europaplatz", "Europaplatz");
        stationMap.put("Linz/Donau Ferihumerstraße", "Ferihumerstraße");
        stationMap.put("Linz/Donau Florianerstraße", "Florianerstraße");
        stationMap.put("Linz/Donau Hillerstraße", "Hillerstraße");
        stationMap.put("Linz/Donau JKU I Universität Nord", "Universität Nord");
        stationMap.put("Linz/Donau Kudlichstraße", "Kudlichstraße");
        stationMap.put("Linz/Donau Lederergasse", "Lederergasse");
        stationMap.put("Linz/Donau Hillerstraße", "Hillerstraße");
        stationMap.put("Linz/Donau, Lunzerstraße Ost", "Lunzerstraße Ost"); //yes, saw the comma as a response
        stationMap.put("Linz/Donau Lunzerstraße Ost", "Lunzerstraße Ost");
        stationMap.put("Linz/Donau Stahlwerk", "Stahlwerk");
        stationMap.put("Linz/Donau St. Magdalena", "St. Magdalena");
        stationMap.put("Linz/Donau Südpark Linz", "Südpark Linz");


        stationMap.put("Linz/Donau Volksgartenstraße", "Volksgartenstraße");
        stationMap.put("Linz/Donau Wildbergstraße", "Wildbergstraße");
        stationMap.put("Linz/Donau Stahlwerk", "Stahlwerk");
        stationMap.put("Linz/Donau Stahlwerk", "Stahlwerk");
        stationMap.put("Linz/Donau Stahlwerk", "Stahlwerk");
        stationMap.put("Linz/Donau Stahlwerk", "Stahlwerk");


        //Linie 1, 2
        stationMap.put("Linz/Donau solarCity", "solarCity");
        stationMap.put("Linz/Donau Auwiesen", "Auwiesen");
        stationMap.put("Linz/Donau JKU I Universität", "JKU Universität");
        stationMap.put("Linz/Donau Remise Kleinmünchen", "Remise Kleinmünchen");
        stationMap.put("Linz/Donau Simonystraße", "Simonystraße");
        stationMap.put("Linz/Donau Sonnensteinstraße", "Sonnensteinstraße");

        //linie 3, 4
        stationMap.put("Linz/Donau Landgutstraße", "Landgutstraße");
        stationMap.put("Traun OÖ Trauner Kreuzung", "Trauner Kreuzung");
        stationMap.put("Traun OÖ Schloss Traun", "Schloss Traun");
        stationMap.put("Leonding Im Bäckerfeld", "Im Bäckerfeld"); //Einrücker in die Remise
        stationMap.put("Leonding Doblerholz", "Doblerholz"); //Einrücker in die Remise

        //linie 11
        stationMap.put("Linz/Donau Wegscheider Straße", "Wegscheider Straße");
        stationMap.put("Leonding Sporthalle Leonding", "Sporthalle Leonding");

        //linie 12
        stationMap.put("Linz/Donau Karlhof", "Karlhof");

        //Linie 17
        stationMap.put("Linz/Donau Industriezeile", "Industriezeile");
        stationMap.put("Hitzing", "Hitzing"); //why no location?
        stationMap.put("Leonding Meixnerkreuzung", "Meixnerkreuzung");

        //Linie 18
        stationMap.put("Linz/Donau, Turmstraße", "Turmstraße");
        stationMap.put("Linz/Donau Turmstraße", "Turmstraße");

        //Linie 19
        stationMap.put("Linz/Donau Pichling", "Pichling");
        stationMap.put("Linz/Donau Pichlinger See", "Pichlinger See");
        stationMap.put("Linz/Donau Fernheizkraftwerk", "Fernheizkraftwerk");

        //linie 25
        stationMap.put("Linz/Donau Kopernikusstraße", "Kopernikusstraße");
        stationMap.put("Linz/Donau Oed", "Oed");

        //linie 26
        stationMap.put("Linz/Donau St. Margarethen", "Margarethen");
        stationMap.put("Linz/Donau Stadion", "Stadion");
        stationMap.put("Linz/Donau Taubenmarkt", "Taubenmarkt");

        //linie 27
        stationMap.put("Linz/Donau Chemiepark", "Chemiepark");
        stationMap.put("Linz/Donau Schiffswerft", "Schiffswerft");

        //linie 33
        stationMap.put("Steyregg Pleschinger See", "Pleschinger See");
        stationMap.put("Linz/Donau Riesenhof", "Riesenhof");

        //linie 38
        stationMap.put("Linz/Donau Jäger im Tal", "Jäger im Tal");

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

        //linie 50
        stationMap.put("Linz/Donau Pöstlingberg", "Pöstlingberg");
        stationMap.put("Linz/Donau Hauptplatz", "Hauptplatz");
        stationMap.put("Linz/Donau Bruckneruniversität", "Bruckneruniversität");

        //linie 101
        stationMap.put("Linz/Donau Urnenhain Urfahr", "Urnenhain Urfahr");
        stationMap.put("Linz/Donau Aichinger", "Aichinger");

        //linie 102
        stationMap.put("Linz/Donau Worathweg", "Worathweg");
        stationMap.put("Linz/Donau Rudolfstraße", "Rudolfstraße");

        //linie 104
        stationMap.put("Linz/Donau Sennweg", "Sennweg");
        stationMap.put("Linz/Donau Waldesruh", "Waldesruh");

        //linie 105
        stationMap.put("Linz/Donau Siriusweg", "Siriusweg");

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
