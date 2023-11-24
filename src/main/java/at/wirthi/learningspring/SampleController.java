package at.wirthi.learningspring;

import at.wirthi.learningspring.dm.Departure;
import at.wirthi.learningspring.dm.DepartureMonitor;
import at.wirthi.learningspring.nametable.NameTable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SampleController {

    @RequestMapping("/stop")
    String stop(@RequestParam("name") String name) { //  localhost:8080/stop?name=WIFI%20Linz%20AG
        System.out.println("stopName: "+name);
        List<Departure> list = DepartureMonitor.nextDeparturesForStop(name);
        String departures = "<html><body>List of next departures: <ul>";
        for (Departure dep : list) {
            departures += "<li>in "+dep.getCountdown() + " min: [[Linie " + dep.getLineNumber() + "]] nach [[Haltestelle " + NameTable.getPublicName(dep.getDirection())+"]]</li>";
        }
        departures += "</ul></body></html>";
        return departures;
    }


}
