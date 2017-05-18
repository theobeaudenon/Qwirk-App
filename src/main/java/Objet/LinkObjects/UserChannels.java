package Objet.LinkObjects;

import Objet.Channel.Channel;
import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Objet.LinkObjects
 * Created by Theo on 18/05/2017 for app.
 */
public class UserChannels implements Serializable {
    public int userID;
    public int channelID;

    public UserChannels(int userID, int channelID) {
        this.userID = userID;
        this.channelID = channelID;
    }
    public UserChannels(String messageJson) {
        Gson gson = new Gson();
        UserChannels grupoAplicacao = gson.fromJson(messageJson, UserChannels.class);
        this.userID = grupoAplicacao.getUserID();
        this.channelID = grupoAplicacao.getChannelID();

    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);

    }
}
