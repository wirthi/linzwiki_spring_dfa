package at.wirthi.learningspring.dm;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class DepartureMonitor {

    private static final String USER_AGENT = "Mozilla/5.0";

    public static void main(String argv[]) {
        nextDeparturesForStop("WIFI%20Linz%20AG");
        nextDeparturesForStop("Linz%2FDonau%2C%20Taubenmarkt");
    }

    public static void errorAndTerminate(String reason, String xml) {
        System.out.println("********************************************");
        System.out.println(xml);
        System.out.println("********************************************");
        System.out.println("Error: " + reason);
        System.out.println("********************************************");
        System.exit(0);
    }

    public static List<Departure> nextDeparturesForStop(String stopName) {
        String urlSessionActivation = "http://linzag.at/static/XML_DM_REQUEST?sessionID=0&locationServerActive=1&type_dm=any&name_dm=" + urlencode(stopName) + "&limit=20";
        System.out.println(urlSessionActivation);
        String first = readStringFromURL(urlSessionActivation);
        System.out.println("=======================================");
        System.out.println(first);

        int idxSessionStart = first.indexOf("sessionID") + 11; //including sessionID="
        int idxSessionEnd = first.indexOf("\"", idxSessionStart);
        System.out.println("Session index: " + idxSessionStart + " " + idxSessionEnd);
        if (idxSessionStart <= 0 || idxSessionEnd <= 0) {
            errorAndTerminate("session not found", first);
        }
        String sessionID = first.substring(idxSessionStart, idxSessionEnd);
        System.out.println("Session: "+sessionID);

        //&locationServerActive=1
        String urlDepRequest = "http://linzag.at/static/XML_DM_REQUEST?sessionID=" + sessionID + "&requestID=1&type_dm=any&dmLineSelectionAll=1&command=dmNext";
        String second = readStringFromURL(urlDepRequest);

        System.out.println("=======================================");
        System.out.println(second);

        List<Departure> departures = nextDeparturesForInput(second);
        System.out.println("done analyzing");
        return departures;
    }

    private static String urlencode(String stopName) {
        try {
            return URLEncoder.encode(stopName,"UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static List<Departure> nextDeparturesForInput(String input) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            Document doc = db.parse(new ByteArrayInputStream(input.getBytes()));

            NodeList nl = doc.getElementsByTagName("itdDeparture");
            List<Departure> departures = new ArrayList<>(10);

            for (int i = 0; i < nl.getLength(); i++) {
                if (nl.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    org.w3c.dom.Element itdDeparture = (org.w3c.dom.Element) nl.item(i);

                    String countdown = itdDeparture.getAttribute("countdown");

                    System.out.println("======");
                    System.out.println(countdown);

                    NodeList servingLines = itdDeparture.getElementsByTagName("itdServingLine");
                    String lineNumber = getItem(servingLines, "number");
                    String direction = getItem(servingLines, "direction");

                    System.out.println(lineNumber);
                    System.out.println(direction);

                    departures.add(new Departure(stoi(countdown), stoi(lineNumber), direction));
                }
            }
            return departures;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static int stoi(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception ex) {
            System.out.println("cannot parse as integer: " + s);
            ex.printStackTrace();
            return -1;
        }
    }

    private static String getItem(NodeList nl, String name) {
        Node node = nl.item(0).getAttributes().getNamedItem(name);
        if (node == null) {
            return "";
        }
        Attr attr = (org.w3c.dom.Attr) node;
        return attr.getValue();
    }

    private static String readStringFromURL(String urlString) {
        try {
            URL url = new URI(urlString).toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            con.disconnect();

            return content.toString();
        } catch (IOException ex) {
            return ex.getMessage();
        } catch (URISyntaxException ex) {
            return ex.getMessage();
        }
    }
}
