package at.wirthi.learningspring.util;

import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;

public class Util {
    private Util() {

    }

    public static final String USER_AGENT = "Mozilla/5.0";

    public static int stoi(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception ex) {
            Log.log("cannot parse as integer: " + s, LogDetail.NORMAL);
            ex.printStackTrace();
            return -1;
        }
    }

    public static String getItem(NodeList nl, String name) {
        Node node = nl.item(0).getAttributes().getNamedItem(name);
        if (node == null) {
            return "";
        }
        Attr attr = (org.w3c.dom.Attr) node;
        return attr.getValue();
    }


    public static void errorAndTerminate(String reason, String xml) {
        Log.log("********************************************", LogDetail.MINIMAL);
        Log.log(xml, LogDetail.MINIMAL);
        Log.log("********************************************", LogDetail.MINIMAL);
        Log.log("Error: " + reason, LogDetail.MINIMAL);
        Log.log("********************************************", LogDetail.MINIMAL);
        System.exit(0);
    }

    public static String urlencode(String stopName) {
        try {
            return URLEncoder.encode(stopName, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String readStringFromURL(String urlString) {
        try {
            URL url = new URI(urlString).toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", Util.USER_AGENT);

            int responseCode = con.getResponseCode();
            Log.log("GET Response Code :: " + responseCode, LogDetail.NORMAL);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
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

    public static String linzWikiLink(String href, String caption) {
        return linzWikiLink(href, caption, null);
    }

    public static String linzWikiLink(String href, String caption, String target) {
        String fTarget = target == null ? "" : "target=\"" + target + "\"";
        return "<a href=\"https://www.linzwiki.at/wiki/" + urlencode(href) + "\" " + fTarget + ">" + caption + "</a>";
    }

    // goal is to sanitize this so that we don't forward bullshit data.
    public static String sanitize(String value) {
        String s = value;
        if (s == null) {
            s = "";
        }
        if (s.length() > 50) {
            s = s.substring(0, 50);
            Log.log("SANITIZED: too long: " + s, LogDetail.NORMAL);
        }
        return s;
    }

    public static String getHTMLHeader() {
        String s = "<html><head>";
        s += "<meta http-equiv=\"Cache-Control\" content=\"no-cache, no-store, must-revalidate\" />";
        s += "<meta http-equiv=\"Expires\" content=\"0\" />";
        s += "<meta http-equiv=\"Pragma\" content=\"no-cache\" />";
        s += "<link rel=\"stylesheet\" href=\"https://www.linzwiki.at/w/load.php?lang=de-at&amp;modules=site.styles&amp;only=styles&amp;skin=vector\"/></head>";
        return s;
    }
}
