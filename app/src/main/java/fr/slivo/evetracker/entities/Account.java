package fr.slivo.evetracker.entities;

import java.util.ArrayList;

import fr.slivo.evetracker.api.XmlParser;

public class Account {

    private ArrayList<Character> listChar = new ArrayList<>();

    protected EveAPI api;


    public Account(){
        //Empty constructor for ORM
    }

    public Account(EveAPI api){

        this.api = api;
    }

    public void retrieveCharacters(){

        try {
            if (!this.api.getKeyId().equals("") || !this.api.getvCode().equals("")) {
                this.listChar = XmlParser.extractXMLchar(this.api);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Getter / Setter
    public EveAPI getApi() {
        return api;
    }
    public ArrayList<Character> getListChar() {
        return listChar;
    }
}
