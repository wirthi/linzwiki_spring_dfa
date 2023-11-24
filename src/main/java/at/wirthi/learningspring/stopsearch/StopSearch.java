package at.wirthi.learningspring.stopsearch;

import at.wirthi.learningspring.linzagdata.Response;
import at.wirthi.learningspring.util.Util;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class StopSearch {
    public static List<String> searchForStop(String stopName) {
        // anyObjFilter_dm=2 => only accept Stops; cf. LinzAG page 33
        String urlSessionActivation = "http://linzag.at/static/XML_DM_REQUEST?sessionID=0&locationServerActive=1&type_dm=any&anyObjFilter_dm=2&name_dm=" + Util.urlencode(stopName) + "&limit=20";
        System.out.println(urlSessionActivation);
        String first = Util.readStringFromURL(urlSessionActivation);
        System.out.println("=======================================");
        System.out.println(first);

        Response response = Response.create(first);
        List<String> answerList = new ArrayList<>(20);

        //<itdOdvName state="list" method="auto">
        //<odvNameElem listIndex="0" selected="1" matchQuality="1000" x="5447215" y="807790" mapName="NAV5" id="73" omc="44001001" placeID="-1" anyType="poi" anyTypeSort="4" locality="Linz/Donau" objectName="Hauptplatz" buildingName="" buildingNumber="" postCode="" streetName="" nameKey="" mainLocality="" stateless="poiID:73:44001001:-1:Hauptplatz:Linz/Donau:Hauptplatz:ANY:POI:5447215:807790:NAV5:LNZ" value="0:1">Linz/Donau, Hauptplatz</odvNameElem>
        NodeList nl = response.getDocument().getElementsByTagName("odvNameElem");
        System.out.println("found odvNameElem: " + nl.getLength());
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                org.w3c.dom.Element odvNameElem = (org.w3c.dom.Element) nl.item(i);
                String locality = odvNameElem.getAttribute("locality");
                String objectName = odvNameElem.getAttribute("objectName");
                String value = odvNameElem.getTextContent();

                System.out.println("locality: " + locality + "; objectName: " + objectName + "; value: " + value);
                answerList.add(value);
            }
        }
        return answerList;
    }
}
