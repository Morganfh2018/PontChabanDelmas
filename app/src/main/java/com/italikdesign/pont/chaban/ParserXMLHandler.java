package com.italikdesign.pont.chaban;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by italikdesign on 29/05/2016.
 */
public class ParserXMLHandler extends DefaultHandler {

    // name of tags XML
    private final String ITEM = "item";
    private final String JOUR = "jour";
    private final String DATE = "date";
    private final String ANNEE = "annee";
    private final String SENS = "sens";
    private final String BATEAUX = "bateaux";
    private final String HEURE1 = "heure1";
    private final String HEURE2 = "heure2";
    private final String MOTIF = "motif";
    private final String HEUREPASSAGE = "heurepassage";
    String xml = null;


    // Array list of feeds
    private ArrayList<Feed> feeds;

    // Boolean on whether we are inside of an item
    private boolean inItem;

    // Feed current
    private Feed currentFeed;

    // Buffer on whether contain the data of tag XML
    private StringBuffer buffer;

    @Override
    public void processingInstruction(String target, String data) throws SAXException {
        super.processingInstruction(target, data);
    }

    public ParserXMLHandler() {
        super();
    }


    // * Method is called once
    // * when starting of analyse of flux xml.


    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        feeds = new ArrayList<Feed>();

    }

    /*
     * Function starting when parser find a tag XML
     * New instance of feed
     */
    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        // We reset the buffer every time it encounters an item
        buffer = new StringBuffer();

        // localName contain the name of tag encounter

        // Encounters a tag ITEM, must instance a new feed
        if (localName.equalsIgnoreCase(ITEM)) {
            this.currentFeed = new Feed();
            inItem = true;
        }


        if (localName.equalsIgnoreCase(JOUR)) {
            // Nothing to do
        }

        if (localName.equalsIgnoreCase(DATE)) {
            // Nothing to do
        }

        if (localName.equalsIgnoreCase(ANNEE)) {
            // Nothing to do
        }
        if (localName.equalsIgnoreCase(SENS)) {
            // Nothing to do
        }
        if (localName.equalsIgnoreCase(BATEAUX)) {
            // Nothing to do
        }
        if (localName.equalsIgnoreCase(HEURE1)) {
            // Nothing to do
        }
        if (localName.equalsIgnoreCase(HEURE2)) {
            // Nothing to do
        }
        if (localName.equalsIgnoreCase(MOTIF)) {
            // Nothing to do
        }
        if (localName.equalsIgnoreCase(HEUREPASSAGE)) {
            // Nothing to do
        }

    }

    // Retrive DOM element

    public Document getDomElement(String xml) {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }

        return doc;
    }

    // Retrive Node element
    public final String getElementValue(Node elem) {
        Node child;
        if (elem != null) {
            if (elem.hasChildNodes()) {
                for (child = elem.getFirstChild(); child != null; child = child
                        .getNextSibling()) {
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    // Retrive Node Value
    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {



        if (localName.equalsIgnoreCase(JOUR)){
            if(inItem){
                // The characters are in the buffer object
                this.currentFeed.setJour(buffer.toString());
                buffer = null;
            }
        }


        if(localName.equalsIgnoreCase(DATE)){
            if(inItem){
                this.currentFeed.setDate(buffer.toString());
                buffer = null;
            }
        }

        if(localName.equalsIgnoreCase(ANNEE)){
            if(inItem){
                this.currentFeed.setAnnee(buffer.toString());
                buffer = null;
            }
        }
        if(localName.equalsIgnoreCase(SENS)){
            if(inItem){
                this.currentFeed.setSens(buffer.toString());
                buffer = null;
            }
        }
        if(localName.equalsIgnoreCase(BATEAUX)){
            if(inItem){
                this.currentFeed.setBateaux(buffer.toString());
                buffer = null;
            }
        }
        if(localName.equalsIgnoreCase(HEURE1)){
            if(inItem){
                this.currentFeed.setHeure1(buffer.toString());
                buffer = null;
            }
        }
        if(localName.equalsIgnoreCase(HEURE2)){
            if(inItem){
                this.currentFeed.setHeure2(buffer.toString());
                buffer = null;
            }
        }
        if(localName.equalsIgnoreCase(MOTIF)){
            if(inItem){
                this.currentFeed.setMotif(buffer.toString());
                buffer = null;
            }
        }
        if(localName.equalsIgnoreCase(HEUREPASSAGE)){
            if(inItem){
                this.currentFeed.setHeurepassage(buffer.toString());
                buffer = null;
            }
        }






        if (localName.equalsIgnoreCase(ITEM)){
            feeds.add(currentFeed);
            inItem = false;
        }

    }



    public void characters(char[] ch,int start, int length)	throws SAXException{
        String lecture = new String(ch,start,length);
        if(buffer != null) buffer.append(lecture);
    }


    // method for get data
    public ArrayList<Feed> getData(){
        return feeds;
    }
}

