package Objet.Bot;

/**
 * Objet.Bot
 * Created by Theo on 17/05/2017 for app.
 */

import java.util.ArrayList;

public class Bot {


    private ArrayList<Commandes> commandes;


    private String name;
    private Integer idBot;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bot(ArrayList<Commandes> commandes, String name,Integer idBot) {
        this.commandes = commandes;
        this.name = name;
        this.idBot = idBot;

    }


    public ArrayList<Commandes> getCommandes() {
        return commandes;
    }

    public Integer getIdBot() {
        return idBot;
    }

    public void setIdBot(Integer idBot) {
        this.idBot = idBot;
    }

    public void setCommandes(ArrayList<Commandes> commandes) {
        this.commandes = commandes;
    }
}