package Objet.Message;

import com.google.gson.Gson;

/**
 * Created by theobeaudenon on 24/04/2017.
 */
public class Message {
    private String userName;
    private String message;
    private Integer date;
    private Integer channel;
    private Integer user;



    public Message(String userName, String message, Integer date, Integer channel,Integer user) {
        super();
        this.userName = userName;
        this.message = message;

        this.date = date;
        this.channel = channel;
        this.user = user;
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


    public String toJson() {
        Gson gson = new Gson();
       return gson.toJson(this);

    }
}
