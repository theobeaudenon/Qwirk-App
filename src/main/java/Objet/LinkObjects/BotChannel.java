package Objet.LinkObjects;

import java.io.Serializable;

/**
 * Objet.LinkObjects
 * Created by Theo on 18/05/2017 for app.
 */
public class BotChannel implements Serializable {
    public int botID;
    public int channelID;

    public BotChannel(int botID, int channelID) {
        this.botID = botID;
        this.channelID = channelID;
    }

    public int getBotID() {
        return botID;
    }

    public void setBotID(int botID) {
        this.botID = botID;
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }
}
