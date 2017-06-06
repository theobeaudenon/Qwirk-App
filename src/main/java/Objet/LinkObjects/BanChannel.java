package Objet.LinkObjects;

import com.google.gson.Gson;

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



    public BanChannel(String messageJson) {
        Gson gson = new Gson();
        BanChannel grupoAplicacao = gson.fromJson(messageJson, BanChannel.class);
        this.banID = grupoAplicacao.getBanID();
        this.channelID = grupoAplicacao.getChannelID();
        this.type = grupoAplicacao.getType();

    }
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    }
