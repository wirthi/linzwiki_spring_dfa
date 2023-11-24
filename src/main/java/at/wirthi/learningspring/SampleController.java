package at.wirthi.learningspring;

import at.wirthi.learningspring.dm.Departure;
import at.wirthi.learningspring.dm.DepartureMonitor;
import at.wirthi.learningspring.nametable.NameTable;
import at.wirthi.learningspring.stopsearch.StopSearch;
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
        String departures = "<html><body>List of next departures: <ul>";
        for (Departure dep : list) {
            departures += "<li>in " + dep.getCountdown() + " min: [[Linie " + dep.getLineNumber() + "]] nach [[Haltestelle " + NameTable.getPublicName(dep.getDirection()) + "]]</li>";
        }
        departures += "</ul></body></html>";
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
