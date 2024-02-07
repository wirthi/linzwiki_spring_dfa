package at.wirthi.learningspring.linzagdata;

import at.wirthi.learningspring.util.Log;
import at.wirthi.learningspring.util.LogDetail;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

public class Response {

    private final Document doc;

    private Response(Document doc) {
        this.doc = doc;
    }

    public static Response create(String input) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();

            Response state = new Response(db.parse(new ByteArrayInputStream(input.getBytes())));
            return state;
        } catch (Exception ex) {
            Log.log(ex.getMessage(), LogDetail.NORMAL);
            return null;
        }
    }

    public Document getDocument() {
        return doc;
    }
}
