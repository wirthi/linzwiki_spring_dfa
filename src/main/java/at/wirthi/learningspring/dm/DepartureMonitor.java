package at.wirthi.learningspring.dm;

import at.wirthi.learningspring.linzagdata.Response;
import at.wirthi.learningspring.util.Log;
import at.wirthi.learningspring.util.LogDetail;
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
        nextDeparturesForStop("WIFI%20Linz%20AG", null);
        nextDeparturesForStop("Linz%2FDonau%2C%20Taubenmarkt", null);
        nextDeparturesForStop("Linz%2FDonau%2C%20Aloisianum", null);
    }

    public static DepartureResponse nextDeparturesForStop(String stopName, String stopID) {
        // anyObjFilter_dm=2 => only accept Stops; cf. LinzAG page 33
        String id = (stopID != null && stopID != "") ? stopID : Util.urlencode(stopName);
        String urlSessionActivation = "http://linzag.at/static/XML_DM_REQUEST?locationServerActive=1&stateless=1&type_dm=stop&anyObjFilter_dm=2&name_dm=" + id + "&limit=20&mode=direct";
        Log.log(urlSessionActivation, LogDetail.NORMAL);
        String responseStr = Util.readStringFromURL(urlSessionActivation);
        Log.log("=======================================", LogDetail.NORMAL);
        Log.log(responseStr, LogDetail.DETAILED);

        Response response = Response.create(responseStr);
        if (DMParser.isPlaceIdentified(response)) {
            List<Departure> departures = DMParser.nextDepartures(response);
            return new DepartureResponse(departures, true, responseStr);
        } else {
            return new DepartureResponse(null, false, responseStr);
        }
    }
}
