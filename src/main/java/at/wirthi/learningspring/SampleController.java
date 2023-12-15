package at.wirthi.learningspring;

import at.wirthi.learningspring.dm.Departure;
import at.wirthi.learningspring.dm.DepartureMonitor;
import at.wirthi.learningspring.nametable.NameTable;
import at.wirthi.learningspring.stopsearch.StopSearch;
import at.wirthi.learningspring.util.Config;
import at.wirthi.learningspring.util.Util;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SampleController {

    /**
     * Digitale Fahrplan-Information. Station eingeben, nächste Abfahrten erhalten.
     *
     * @param paramName Stationsname oder Straßenname
     * @param detailed  wenn leer oder false, dann Kurzversion, sonst lange
     * @return Auskunft über die nächsten Abfahrten an dieser Haltestelle
     */
    @RequestMapping("/dm")
    String dm(
            @RequestParam("name") String paramName,
            @RequestParam(name = "detailed", required = false) String paramDetailed
    ) { //  localhost:8080/dm?name=WIFI%20Linz%20AG
        System.out.println("name: " + paramName);
        boolean detailed = !(paramDetailed == null || paramDetailed.equals("false"));

        int minuteLimit = detailed ? 120 : Config.dpMinuteRange;
        int countLimit = detailed ? Integer.MAX_VALUE : Config.dpCountLimit;

        List<Departure> list = DepartureMonitor.nextDeparturesForStop(paramName);
        String departures = "<html><head><link rel=\"stylesheet\" href=\"https://www.linzwiki.at/w/load.php?lang=de-at&amp;modules=site.styles&amp;only=styles&amp;skin=vector\"/></head><body>Fahrplanmäßige Abfahrten " + paramName + ": <br />";
        int count = 0;
        for (Departure dep : list) {
            if (dep.getCountdown() <= minuteLimit && count < countLimit) {
                count++;
                String targetStop = NameTable.getPublicName(dep.getDirection());
                String line = "Linie " + dep.getLineNumber();
                String stopHref = "Haltestelle " + targetStop;
                String stopCaption = "Hst " + targetStop;
                departures += "in " + dep.getCountdown() + " min: " + Util.linzWikiLink(line, line, "_top") + " Richtung " + Util.linzWikiLink(stopHref, stopCaption, "_top") + "<br />";
            }
        }
        if (count == 0) {
            departures += "in den nächsten " + minuteLimit + " Minuten keine Abfahrten<br />";
        }
        if (detailed) {
            // options to select here
        } else {
            departures += "<a href=\"dm?name=" + paramName + "&detailed=true\" target=\"_blank\">mehr Details</a><br />";
        }
        departures += "<br /><b>Achtung</b>: Fahrplanzeiten! Keine Echtzeitdaten.</body></html>";
        return departures;
    }

    /**
     * Haltestellen-Suche. Name eingeben, mögliche gültige Suchanfragen erhalten
     *
     * @param name Stationsname oder Straßenname
     * @return Möglicherweise gemeinte Stationsnamen
     */
    @RequestMapping("/stop")
    String stop(@RequestParam("name") String name) { //  localhost:8080/stop?name=WIFI%20Linz%20AG
        System.out.println("name: " + name);
        List<String> list = StopSearch.searchForStop(name);
        String answer = "<html><body>You searched for <b>" + name + "</b>, possible locations are: <ul>";
        for (String loc : list) {
            answer += "<li>" + loc + "</li>";
        }
        answer += "</ul></body></html>";
        return answer;
    }


}
