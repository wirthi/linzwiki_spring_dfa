package at.wirthi.learningspring.dm;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class DepartureMonitor {

    private static final String USER_AGENT = "Mozilla/5.0";

    public static void main(String argv[]) {
        nextDeparturesForStop("WIFI%20Linz%20AG");
        nextDeparturesForStop("Linz%2FDonau%2C%20Taubenmarkt");
        nextDeparturesForStop("Linz%2FDonau%2C%20Aloisianum");
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

        String urlDepRequest = "http://linzag.at/static/XML_DM_REQUEST?sessionID=" + sessionID + "&requestID=1&dmLineSelectionAll=1";
        String second = readStringFromURL(urlDepRequest);

        System.out.println("=======================================");
        System.out.println(second);

        DMState state = DMState.create(second);
        if (state.isPlaceIdentified()) {
            System.out.println("identified");
        } else {
            System.out.println("not identified");
        }
        List<Departure> departures = state.nextDepartures();
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
