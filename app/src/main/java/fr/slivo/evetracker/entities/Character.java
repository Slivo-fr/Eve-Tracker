package fr.slivo.evetracker.entities;

import java.util.ArrayList;

import fr.slivo.evetracker.api.XmlParser;


public class Character {

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public void setCharId(String charId) {
        this.charId = charId;
    }

    private String corpName;
    private String charId;
    private String name;
    private Account account;
    private ArrayList<EveNotif> listNotif = new ArrayList<>();

    public Character(){
        //Empty constructor for ORM
    }


    public Character(String name, String corpName, String charId, String[] apiInfos){

        this.corpName = corpName;
        this.name = name;
        this.charId = charId;
    }

    public void retrieveNotifications(EveAPI api){

        try {
            this.listNotif = XmlParser.extractXMLNotif(api, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Getter / Setter

    public String getCorpName() {
        return corpName;
    }

    public String getCharId() {
        return charId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<EveNotif> getListNotif() {
        return listNotif;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
