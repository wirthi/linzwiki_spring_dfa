package at.wirthi.learningspring.util;

import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Util {
    private Util() {

    }

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

}
