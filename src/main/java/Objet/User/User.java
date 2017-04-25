package Objet.User;

import Objet.Message.Message;
import com.google.gson.Gson;

/**
 * Created by theobeaudenon on 25/04/2017.
 */
public class User {
    String userName;
    String mail;
    String pass;

    public User(String userName, String mail, String pass) {
        this.userName = userName;
        this.mail = mail;
        this.pass = pass;
    }


    public User(String messageJson) {
        Gson gson = new Gson();
        User grupoAplicacao = gson.fromJson(messageJson, User.class);
        this.mail = grupoAplicacao.getMail();
        this.userName = grupoAplicacao.getUserName();
        this.pass = grupoAplicacao.getPass();

    }

    public User(String mail, String pass) {
        this.mail = mail;
        this.pass = pass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);

    }

}
