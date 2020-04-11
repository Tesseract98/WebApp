package server.sax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import server.sax.reflection.ReflectionHelper;

public class SAXHandler extends DefaultHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SAXHandler.class);

    private static final String CLASSNAME = "class";
    private String element = null;
    private Object object = null;

    public void startDocument() {
        System.out.println("Start document");
    }

    public void endDocument() {
        System.out.println("End document ");
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (!qName.equals(CLASSNAME)) {
            element = qName;
        } else {
            String className = attributes.getValue(0);
            LOGGER.debug("Class name: " + className);
            object = ReflectionHelper.createInstance(className);
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        element = null;
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        if (element != null) {
            String value = new String(ch, start, length);
            System.out.println(element + " = " + value);
            ReflectionHelper.setFieldValue(object, element, value);
        }
    }

    public Object getObject() {
        return object;
    }

}
