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
            System.out.println("cannot parse as integer: " + s);
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
        System.out.println("********************************************");
        System.out.println(xml);
        System.out.println("********************************************");
        System.out.println("Error: " + reason);
        System.out.println("********************************************");
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
            System.out.println("GET Response Code :: " + responseCode);

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
        if (s.length() > 50) {
            s = s.substring(0, 50);
            System.out.println("SANITIZED: too long: " + s);
        }
        return s;
    }
}
