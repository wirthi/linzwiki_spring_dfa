package at.wirthi.learningspring.dm;

import at.wirthi.learningspring.linzagdata.Response;
import at.wirthi.learningspring.util.Util;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class DMParser {

    public static boolean isPlaceIdentified(Response response) {
        //<itdOdvPlace state="identified" method="itp">
        NodeList nl = response.getDocument().getElementsByTagName("itdOdvPlace");
        System.out.println("found itdOdvPlace: " + nl.getLength());
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                org.w3c.dom.Element itdOdvPlace = (org.w3c.dom.Element) nl.item(i);
                String state = itdOdvPlace.getAttribute("state");
                System.out.println("state: " + state);
                return state.equalsIgnoreCase("identified");
            }
        }
        return false;
    }

    public static List<Departure> nextDepartures(Response response) {
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

                    System.out.println(countdown + " " + lineNumber + " " + direction);

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
