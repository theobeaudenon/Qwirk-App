package Objet.Contacts;

import Objet.Utils.Action;
import com.google.gson.Gson;

/**
 * Objet.Contacts
 * Created by Theo on 17/05/2017 for app.
 */
public class Contact {
    private Integer idUser1;

    private Integer idUser2;

    private String mail;

    private Action action;


    public Contact(Integer idUser2,  Integer idUser1 ,Action action) {

        this.idUser2 = idUser2;
        this.idUser1 = idUser1;
        this.action = action;
    }

    public Contact(Integer idUser2,  Integer idUser1 ,Action action, String mail) {

        this.idUser2 = idUser2;
        this.idUser1 = idUser1;
        this.action = action;
        this.mail = mail;
    }

    public Contact(String messageJson) {
        Gson gson = new Gson();
        Contact grupoAplicacao = gson.fromJson(messageJson, Contact.class);
        this.idUser1 = grupoAplicacao.getIdUser1();
        this.idUser2 = grupoAplicacao.getIdUser2();
        this.action = grupoAplicacao.getAction();

    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Integer getIdUser1() {
        return idUser1;
    }

    public void setIdUser1(Integer idUser1) {
        this.idUser1 = idUser1;
    }

    public Integer getIdUser2() {
        return idUser2;
    }

    public void setIdUser2(Integer idUser2) {
        this.idUser2 = idUser2;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);

    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
