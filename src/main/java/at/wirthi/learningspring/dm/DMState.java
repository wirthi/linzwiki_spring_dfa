package at.wirthi.learningspring.dm;

import at.wirthi.learningspring.util.Util;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class DMState {

    private final Document doc;

    private DMState(Document doc) {
        this.doc = doc;
    }

    public static DMState create(String input) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();

            DMState state = new DMState(db.parse(new ByteArrayInputStream(input.getBytes())));
            return state;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public Document getDocument() {
        return doc;
    }

    public boolean isPlaceIdentified() {
        //<itdOdvPlace state="identified" method="itp">
        NodeList nl = doc.getElementsByTagName("itdOdvPlace");
        System.out.println("found itdOdvPlace: "+nl.getLength());
        for (int i=0;i<nl.getLength();i++) {
            if (nl.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                org.w3c.dom.Element itdOdvPlace = (org.w3c.dom.Element) nl.item(i);
                String state = itdOdvPlace.getAttribute("state");
                System.out.println("state: "+state);
                return state.equalsIgnoreCase("identified");
            }
        }
        return false;
    }

    public List<Departure> nextDepartures() {
        try {
            NodeList nl = doc.getElementsByTagName("itdDeparture");
            List<Departure> departures = new ArrayList<>(10);

            for (int i = 0; i < nl.getLength(); i++) {
                if (nl.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    org.w3c.dom.Element itdDeparture = (org.w3c.dom.Element) nl.item(i);

                    String countdown = itdDeparture.getAttribute("countdown");

                    System.out.println("======");
                    System.out.println(countdown);

                    NodeList servingLines = itdDeparture.getElementsByTagName("itdServingLine");
                    String lineNumber = Util.getItem(servingLines, "number");
                    String direction = Util.getItem(servingLines, "direction");

                    System.out.println(lineNumber);
                    System.out.println(direction);

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
