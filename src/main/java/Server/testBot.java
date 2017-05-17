package Server;

import Objet.Bot.ActionBot;
import Objet.Bot.Bot;
import Objet.Bot.Commandes;
import Objet.Utils.Action;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

/**
 * Server
 * Created by Theo on 17/05/2017 for app.
 */
public class testBot {
    public static void main(String[] args) {
          String bot1 = "{\n" +
                "  \"commandes\": [\n" +
                "    {\n" +
                "      \"action\": \"KICK\",\n" +
                "      \"cmd\": \"kick\",\n" +
                "      \"message\": \"Le marteau du ban a frappé\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"action\": \"BAN\",\n" +
                "      \"cmd\": \"ban\",\n" +
                "      \"message\": \"Le marteau du ban a frappé\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"action\": \"MESSAGE\",\n" +
                "      \"cmd\": \"hey\",\n" +
                "      \"message\": \"Coucou\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"name\": \"nom du bot\"\n" +
                "}";

          try {
              JsonParser parser = new JsonParser();
              JsonObject rootObj = parser.parse(bot1).getAsJsonObject();

              ArrayList<Commandes> commandes = new ArrayList<Commandes>();

              JsonArray locObj = rootObj.getAsJsonArray("commandes");
              for (JsonElement pa : locObj) {
                  JsonObject paymentObj = pa.getAsJsonObject();
                  String     action     = paymentObj.get("action").getAsString();
                  String     quoteid     = paymentObj.get("cmd").getAsString();
                  String     message     = paymentObj.get("message").getAsString();
                  ActionBot actionBot=null;
                  if(ActionBot.BAN.toString().equals(action)){
                      actionBot = ActionBot.BAN;
                  }
                  if(ActionBot.MESSAGE.toString().equals(action)){
                      actionBot = ActionBot.MESSAGE;
                  }
                  if(ActionBot.KICK.toString().equals(action)){
                      actionBot = ActionBot.KICK;
                  }
                  if(action== null){
                      throw new Exception();
                  }
                  commandes.add(new Commandes(actionBot,quoteid,message));
              }
            //  Bot name = new Bot(commandes, rootObj.get("name").getAsString());

             // System.out.printf(name.getName());
          }catch (Exception e){

          }

    }
}
