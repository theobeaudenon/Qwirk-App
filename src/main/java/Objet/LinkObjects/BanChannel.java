package Objet.LinkObjects;

import java.io.Serializable;

/**
 * Objet.LinkObjects
 * Created by Theo on 18/05/2017 for app.
 */
public class BanChannel implements Serializable {
    public int banID;
    public int channelID;

    public String type;


    public BanChannel(int banID, int channelID,String type) {
        this.banID = banID;
        this.channelID = channelID;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBanID() {
        return banID;
    }

    public void setBanID(int botID) {
        this.banID = botID;
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }
}
