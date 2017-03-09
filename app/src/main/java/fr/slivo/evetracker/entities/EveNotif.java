package fr.slivo.evetracker.entities;

public class EveNotif {

    public void setNotifId(String notifId) {
        this.notifId = notifId;
    }

    public void setNotifType(String notifType) {
        this.notifType = notifType;
    }

    private String notifId;
    private String notifType;
    private String read;
    private String senderName;


    public EveNotif(){
        //Empty constructor for ORM
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public EveNotif(String notifId, String notifType, String read, String sender){

        this.notifId = notifId;
        this.notifType = notifType;
        this.read = read;
        this.senderName = sender;
    }

    // Getter / Setter

    public String getNotifId() {
        return notifId;
    }

    public String getNotifType() {
        return notifType;
    }

    public boolean isRead() {
        if (read == "1")
            return true;
        else
            return false;
    }

    public void setRead(String read) {
        this.read = read;
    }

}
