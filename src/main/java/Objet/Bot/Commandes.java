package Objet.Bot;

import java.io.Serializable;

/**
 * Objet.Bot
 * Created by Theo on 17/05/2017 for app.
 */
public class Commandes implements Serializable{
    ActionBot actionBot;
    String value;
    String message;

    public Commandes(ActionBot actionBot, String value, String message) {
        this.actionBot = actionBot;
        this.value = value;
        this.message = message;
    }

    public ActionBot getActionBot() {
        return actionBot;
    }

    public void setActionBot(ActionBot actionBot) {
        this.actionBot = actionBot;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
