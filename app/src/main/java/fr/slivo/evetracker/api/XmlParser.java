package fr.slivo.evetracker.api;


import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import fr.slivo.evetracker.entities.Character;
import fr.slivo.evetracker.entities.EveAPI;
import fr.slivo.evetracker.entities.EveNotif;

public class XmlParser {

    public static ArrayList<Character> extractXMLchar(EveAPI api) throws ParserConfigurationException, IOException, SAXException {

        String rep = "";
        HttpsQuery hQuery = new HttpsQuery();
        String query = "https://api.eveonline.com/account/Characters.xml.aspx?" +
                "keyID=" + api.getKeyId() +
                "&vCode=" + api.getvCode();

        try {
            rep = hQuery.execute(query).get();
            Log.d("XML parser Char", query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(rep.getBytes("utf-8"))));
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("row");

        ArrayList<Character> chars = new ArrayList<>();

        for (int i = 0; i < nList.getLength(); i++) {

            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;

                Character character = new Character();
                character.setName(eElement.getAttribute("name"));
                character.setCorpName(eElement.getAttribute("corporationName"));
                character.setCharId(eElement.getAttribute("characterID"));

                chars.add(i, character);
            }
        }
        return chars;
    }

    public static ArrayList<EveNotif> extractXMLNotif(EveAPI api, Character toon) throws ParserConfigurationException, IOException, SAXException {

        String rep = "";
        HttpsQuery hQuery = new HttpsQuery();
        String query = "https://api.eveonline.com/char/Notifications.xml.aspx?" +
                "characterID=" + toon.getCharId() +
                "&keyID=" + api.getKeyId() +
                "&vCode=" + api.getvCode();

        try {
           rep = hQuery.execute(query).get();
            Log.d("XML parser Notif", query);
        } catch (Exception e) {
           e.printStackTrace();
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(rep.getBytes("utf-8"))));
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("row");
        ArrayList<EveNotif> notifications = new ArrayList<>();

        for (int i = 0; i < nList.getLength(); i++) {

            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                EveNotif notification = new EveNotif();
                notification.setNotifId(eElement.getAttribute("notificationID"));
                notification.setNotifType(eElement.getAttribute("typeID"));
                notification.setRead(eElement.getAttribute("read"));
                notification.setSenderName(eElement.getAttribute("senderName"));

                notifications.add(i, notification);
            }
        }
        return notifications;
    }
}

