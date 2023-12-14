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
     * @param name Stationsname oder Straßenname
     * @return Auskunft über die nächsten Abfahrten an dieser Haltestelle
     */
    @RequestMapping("/dm")
    String dm(@RequestParam("name") String name) { //  localhost:8080/dm?name=WIFI%20Linz%20AG
        System.out.println("name: " + name);
        List<Departure> list = DepartureMonitor.nextDeparturesForStop(name);
        String departures = "<html><head><link rel=\"stylesheet\" href=\"https://www.linzwiki.at/w/load.php?lang=de-at&amp;modules=site.styles&amp;only=styles&amp;skin=vector\"/></head><body>Nächste fahrplanmäßige Abfahrten: <br />";
        int count = 0;
        for (Departure dep : list) {
            if (dep.getCountdown() <= Config.dpMinuteRange) {
                count++;
                String targetStop = NameTable.getPublicName(dep.getDirection());
                departures += "in " + dep.getCountdown() + " min: " + Util.linzWikiLink("Linie " + dep.getLineNumber(), "_top") + " nach " + Util.linzWikiLink("Haltestelle " + targetStop, "_top") + "<br />";
            }
        }
        if (count == 0) {
            departures += "in den nächsten " + Config.dpMinuteRange + " Minuten keine Abfahrten<br />";
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
