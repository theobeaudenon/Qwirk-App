package Objet.Message;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by theobeaudenon on 24/04/2017.
 */
public class Message implements Serializable{
    private String userName;
    private String message;
    private Integer date;
    private Integer channel;
    private Integer user;

    @SerializedName("image")
    private boolean isImage;
    private byte[] data;
    private String dataName;



    public Message(String userName, String message, Integer date, Integer channel,Integer user, boolean isImage, byte[] data,String dataName) {
        super();
        this.userName = userName;
        this.message = message;

        this.date = date;
        this.channel = channel;
        this.user = user;
        this.isImage = isImage;
        this.data = data;
        this.dataName = dataName;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Message(String messageJson) {
        Gson gson = new Gson();
        Message grupoAplicacao = gson.fromJson(messageJson, Message.class);
        this.message = grupoAplicacao.getMessage();
        this.userName = grupoAplicacao.getUserName();
        this.date = grupoAplicacao.getDate();
        this.channel = grupoAplicacao.getChannel();
        this.user = grupoAplicacao.getUser();


        this.isImage = grupoAplicacao.getImage();
        this.data = grupoAplicacao.getData();
        this.dataName = grupoAplicacao.getDataName();


    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getDate() {
        return date;
    }


    public Integer getChannel() {
        return channel;
    }

    public boolean getImage() {
        return isImage;
    }

    public void setImage(boolean image) {
        isImage = image;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String toJson() {
        Gson gson = new Gson();
       return gson.toJson(this);

    }
}
