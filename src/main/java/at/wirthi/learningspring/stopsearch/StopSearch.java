package at.wirthi.learningspring.stopsearch;

import at.wirthi.learningspring.linzagdata.Response;
import at.wirthi.learningspring.util.Util;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class StopSearch {
    public static List<LinzAGStop> searchForStop(String stopName) {

        //http://www.linzag.at/static/XML_STOPFINDER_REQUEST?stateless=1&locationServerActive=1&outputFormat=JSON&type_sf=any&name_sf=Hauptbahnhof

        String urlSessionActivation = "https://www.linzag.at/static/XML_STOPFINDER_REQUEST?stateless=1&locationServerActive=1&type_sf=stop&name_sf=" + Util.urlencode(stopName);
        System.out.println(urlSessionActivation);
        String first = Util.readStringFromURL(urlSessionActivation);
        System.out.println("=======================================");
        System.out.println(first);

        Response response = Response.create(first);
        List<LinzAGStop> answerList = new ArrayList<>(20);

        NodeList nl = response.getDocument().getElementsByTagName("odvNameElem");
        System.out.println("found odvNameElem: " + nl.getLength());
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                org.w3c.dom.Element odvNameElem = (org.w3c.dom.Element) nl.item(i);
                String stopID = odvNameElem.getAttribute("stopID");
                String value = odvNameElem.getTextContent();

                System.out.println("stopID: " + stopID + "; value: " + value);
                answerList.add(new LinzAGStop(value, stopID));
            }
        }
        return answerList;
    }
}
