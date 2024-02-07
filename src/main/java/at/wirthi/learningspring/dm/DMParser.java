package at.wirthi.learningspring.dm;

import at.wirthi.learningspring.linzagdata.Response;
import at.wirthi.learningspring.util.Log;
import at.wirthi.learningspring.util.LogDetail;
import at.wirthi.learningspring.util.Util;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class DMParser {

    public static boolean isPlaceIdentified(Response response) {
        if (response == null) {
            return false;
        }
        //<itdOdvPlace state="identified" method="itp">
        NodeList nl = response.getDocument().getElementsByTagName("itdOdvPlace");
        Log.log("found itdOdvPlace: " + nl.getLength(), LogDetail.NORMAL);
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                org.w3c.dom.Element itdOdvPlace = (org.w3c.dom.Element) nl.item(i);
                String state = itdOdvPlace.getAttribute("state");
                Log.log("state: " + state, LogDetail.NORMAL);
                return state.equalsIgnoreCase("identified");
            }
        }
        return false;
    }

    public static List<Departure> nextDepartures(Response response) {
        if (response == null) {
            return null;
        }
        try {
            NodeList nl = response.getDocument().getElementsByTagName("itdDeparture");
            List<Departure> departures = new ArrayList<>(10);

            for (int i = 0; i < nl.getLength(); i++) {
                if (nl.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    org.w3c.dom.Element itdDeparture = (org.w3c.dom.Element) nl.item(i);

                    String countdown = itdDeparture.getAttribute("countdown");

                    NodeList servingLines = itdDeparture.getElementsByTagName("itdServingLine");
                    String lineNumber = Util.getItem(servingLines, "number");
                    String direction = Util.getItem(servingLines, "direction");

                    Log.log(countdown + " " + lineNumber + " " + direction, LogDetail.NORMAL);

                    departures.add(new Departure(Util.stoi(countdown), Util.stoi(lineNumber), direction));
                }
            }
            return departures;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
