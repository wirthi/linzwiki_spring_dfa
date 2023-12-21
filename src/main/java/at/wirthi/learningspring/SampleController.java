package at.wirthi.learningspring;

import at.wirthi.learningspring.dm.Departure;
import at.wirthi.learningspring.dm.DepartureMonitor;
import at.wirthi.learningspring.dm.DepartureResponse;
import at.wirthi.learningspring.nametable.NameTable;
import at.wirthi.learningspring.stopsearch.LinzAGStop;
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
     * @param paramStopID eindeutige ID der LinzAG
     * @param paramDetailed  wenn leer oder false, dann Kurzversion, sonst lange
     * @return Auskunft über die nächsten Abfahrten an dieser Haltestelle
     */
    @RequestMapping("/dm")
    String dm(
            @RequestParam(name = "name") String paramName,
            @RequestParam(name = "stopID", required = false) String paramStopID,
            @RequestParam(name = "detailed", required = false) String paramDetailed
    ) { //  localhost:8080/dm?name=WIFI%20Linz%20AG
        System.out.println("name: " + paramName + " stopID: " + paramStopID);
        boolean detailed = !(paramDetailed == null || paramDetailed.equals("false"));

        int minuteLimit = detailed ? 120 : Config.dpMinuteRange;
        int countLimit = detailed ? Integer.MAX_VALUE : Config.dpCountLimit;

        DepartureResponse response = DepartureMonitor.nextDeparturesForStop(paramName, paramStopID);
        String departures = "<html><head><link rel=\"stylesheet\" href=\"https://www.linzwiki.at/w/load.php?lang=de-at&amp;modules=site.styles&amp;only=styles&amp;skin=vector\"/></head><body>";
        int count = 0;
        if (response.isStopIdentified() && response.getList() != null) {
            departures += "<b>Fahrplanmäßige</b> Abfahrten <b>" + paramName + "</b><br /><br />";
            for (Departure dep : response.getList()) {
                if (dep.getCountdown() <= minuteLimit && count < countLimit) {
                    count++;
                    String targetStop = NameTable.getPublicName(dep.getDirection());
                    String line = "Linie " + dep.getLineNumber();
                    String stopHref = "Haltestelle " + targetStop;
                    String stopCaption = targetStop;
                    departures += "in " + dep.getCountdown() + " m " + Util.linzWikiLink(line, line, "_top") + " nach " + Util.linzWikiLink(stopHref, stopCaption, "_top") + "<br />";
                }
            }
            if (count == 0) {
                departures += "in den nächsten " + minuteLimit + " Minuten keine Abfahrten<br />";
            }
            departures += "<br />";
            if (detailed) {
                // options to select here
                departures += "<a href=\"#\" onclick=\"location.reload(true)\">AKTUALISIEREN</a> ";
                departures += "<br />";
            } else {
                departures += "<a href=\"#\" onclick=\"location.reload(true)\">AKTUALISIEREN</a> ";
                departures += "<a href=\"dm?name=" + Util.sanitize(paramName) + "&stopID=" + Util.sanitize(paramStopID) + "&detailed=true\" target=\"_blank\">mehr Details</a> ";
                departures += "<br />";
            }
        } else {
            List<LinzAGStop> list = StopSearch.analyzeResponse(response.getResponseStr());
            departures += "Haltestelle nicht eindeutig identifiziert. Möglichkeiten:<br /><ul>";
            for (LinzAGStop stop : list) {
                departures += "<li><a href=\"dm?stopID=" + Util.sanitize(stop.getStopID()) + "&name=" + Util.sanitize(stop.getName()) + "\">" + stop.getName() + "</a></li>";
            }
            departures += "</ul>";
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
        List<LinzAGStop> list = StopSearch.searchForStop(name);
        String answer = "<html><body>You searched for <b>" + name + "</b>, possible locations are: <ul>";
        for (LinzAGStop stop : list) {
            answer += "<li><a href=\"dm?stopID=" + Util.sanitize(stop.getStopID()) + "&name=" + Util.sanitize(stop.getName()) + "\">" + stop.getName() + "</a></li>";
        }
        answer += "</ul></body></html>";
        return answer;
    }


}
