package at.wirthi.learningspring.stopsearch;

import at.wirthi.learningspring.linzagdata.Response;
import at.wirthi.learningspring.util.Log;
import at.wirthi.learningspring.util.LogDetail;
import at.wirthi.learningspring.util.Util;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class StopSearch {
    public static List<LinzAGStop> searchForStop(String stopName) {
        //http://www.linzag.at/static/XML_STOPFINDER_REQUEST?stateless=1&locationServerActive=1&outputFormat=JSON&type_sf=any&name_sf=Hauptbahnhof

        String urlSessionActivation = "https://www.linzag.at/static/XML_STOPFINDER_REQUEST?stateless=1&locationServerActive=1&type_sf=stop&name_sf=" + Util.urlencode(stopName);
        Log.log(urlSessionActivation, LogDetail.NORMAL);
        String responseStr = Util.readStringFromURL(urlSessionActivation);
        Log.log("=======================================", LogDetail.NORMAL);
        Log.log(responseStr, LogDetail.DETAILED);
        return analyzeResponse(responseStr);
    }

    public static List<LinzAGStop> analyzeResponse(String responseStr) {
        Response response = Response.create(responseStr);
        List<LinzAGStop> answerList = new ArrayList<>(20);

        NodeList nl = response.getDocument().getElementsByTagName("odvNameElem");
        Log.log("found odvNameElem: " + nl.getLength(), LogDetail.NORMAL);
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                org.w3c.dom.Element odvNameElem = (org.w3c.dom.Element) nl.item(i);
                String stopID = odvNameElem.getAttribute("stopID");
                String value = odvNameElem.getTextContent();

                Log.log("stopID: " + stopID + "; value: " + value, LogDetail.NORMAL);
                answerList.add(new LinzAGStop(value, stopID));
            }
        }
        return answerList;
    }
}
