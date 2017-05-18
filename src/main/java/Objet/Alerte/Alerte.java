package Objet.Alerte;

import Objet.Channel.Channel;
import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Objet.Alerte
 * Created by Theo on 18/05/2017 for app.
 */
public class Alerte implements Serializable {
    String head;
    String message;

    public Alerte(String head, String message) {
        this.head = head;
        this.message = message;
    }

    public Alerte(String messageJson) {
        Gson gson = new Gson();
        Alerte grupoAplicacao = gson.fromJson(messageJson, Alerte.class);
        this.head = grupoAplicacao.getHead();
        this.message = grupoAplicacao.getMessage();
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
