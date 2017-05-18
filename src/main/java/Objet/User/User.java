package Objet.User;

import Objet.Message.Message;
import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by theobeaudenon on 25/04/2017.
 */
public class User implements Serializable{
    String userName;
    String mail;
    String pass;
    Integer userID;
    String verif;

    public User(Integer userID ,String userName, String mail, String pass) {
        this.userName = userName;
        this.mail = mail;
        this.pass = pass;
        this.userID = userID;
    }


    public User(String messageJson) {
        Gson gson = new Gson();
        User grupoAplicacao = gson.fromJson(messageJson, User.class);
        this.mail = grupoAplicacao.getMail();
        this.userName = grupoAplicacao.getUserName();
        this.pass = grupoAplicacao.getPass();
        this.userID = grupoAplicacao.getUserID();
        this.verif = grupoAplicacao.getVerif();

    }

    public User(String mail, String pass) {
        this.mail = mail;
        this.pass = pass;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
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

    public Integer getUserID() {
        return userID;
    }

    public String getVerif() {
        return verif;
    }

    public void setVerif(String verif) {
        this.verif = verif;
    }
}
