package at.wirthi.learningspring.dm;

import at.wirthi.learningspring.linzagdata.Response;
import at.wirthi.learningspring.util.Session;
import at.wirthi.learningspring.util.Util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class DepartureMonitor {

    public static void main(String argv[]) {
        nextDeparturesForStop("WIFI%20Linz%20AG");
        nextDeparturesForStop("Linz%2FDonau%2C%20Taubenmarkt");
        nextDeparturesForStop("Linz%2FDonau%2C%20Aloisianum");
    }

    public static List<Departure> nextDeparturesForStop(String stopName) {
        // anyObjFilter_dm=2 => only accept Stops; cf. LinzAG page 33
        String urlSessionActivation = "http://linzag.at/static/XML_DM_REQUEST?sessionID=0&locationServerActive=1&type_dm=any&anyObjFilter_dm=2&name_dm=" + Util.urlencode(stopName) + "&limit=20";
        System.out.println(urlSessionActivation);
        String first = Util.readStringFromURL(urlSessionActivation);
        System.out.println("=======================================");
        System.out.println(first);

        String sessionID = Session.findSession(first);
        System.out.println("Session: "+sessionID);

        String urlDepRequest = "http://linzag.at/static/XML_DM_REQUEST?sessionID=" + sessionID + "&requestID=1&dmLineSelectionAll=1";
        String second = Util.readStringFromURL(urlDepRequest);

        System.out.println("=======================================");
        System.out.println(second);

        Response response = Response.create(second);
        if (DMParser.isPlaceIdentified(response)) {
            System.out.println("identified");
        } else {
            System.out.println("not identified");
        }
        List<Departure> departures = DMParser.nextDepartures(response);
        System.out.println("done analyzing");
        return departures;
    }
}
