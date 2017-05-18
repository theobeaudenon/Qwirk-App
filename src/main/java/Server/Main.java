package Server;

import Objet.Bot.ActionBot;
import Objet.Bot.Bot;
import Objet.Bot.Commandes;
import Objet.LinkObjects.BotChannel;
import Server.Data.Singleton_Data;
import Server.Events.Channels.Channels_Events;
import Server.Events.Contacts.Contacts_Events;
import Server.Events.Login_Signup.Login_Signup_Events;
import Server.Events.Messages.Messages_Events;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by theobeaudenon on 24/04/2017.
 */
public class Main {

    public static void main(String[] args) {

        //Creation du signleton de données
        Singleton_Data.getInstance();

        //Demarage du serveur
        Configuration config = new Configuration();
        config.setHostname("10.29.16.55");
        config.setPort(9092);
        config.setUpgradeTimeout(10000000);
        config.setPingTimeout(10000000);
        config.setPingInterval(10000000);

        SocketIOServer server = new SocketIOServer(config);


        String bot1 = "{\n" +
                "  \"commandes\": [\n" +
                "    {\n" +
                "      \"action\": \"KICK\",\n" +
                "      \"cmd\": \":kick\",\n" +
                "      \"message\": \"Le marteau du ban a frappé\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"action\": \"BAN\",\n" +
                "      \"cmd\": \":ban\",\n" +
                "      \"message\": \"Le marteau du ban a frappé\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"action\": \"MESSAGE\",\n" +
                "      \"cmd\": \":bite\",\n" +
                "      \"message\": \"chatte\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"action\": \"MESSAGE\",\n" +
                "      \"cmd\": \":hey\",\n" +
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
            Bot name = new Bot(commandes, rootObj.get("name").getAsString(),Singleton_Data.getInstance().getBotIncrement());
            Singleton_Data.getInstance().getBotArrayList().add(name);
            Singleton_Data.getInstance().getBotChannelHashMap().add(new BotChannel(name.getIdBot(),6));

         }catch (Exception e){

        }



        FileOutputStream fout = null;
        ObjectOutputStream oos = null;

        try {

            fout = new FileOutputStream("address.ser");
            oos = new ObjectOutputStream(fout);
            oos.writeObject(Singleton_Data.getInstance());

            System.out.println("Done");

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }



        //LOGIN SIGNUP EVENTS
        Login_Signup_Events.socketLoginEvent(server);
        Login_Signup_Events.socketSingupEvent(server);


        //channels EVENT
        Channels_Events.getPublicChannels(server);
        Channels_Events.getMyChannels(server);
        Channels_Events.channelOpperation(server);

        //messages Events
        Messages_Events.newMessage(server);
        Messages_Events.getAllmessagesFromChannel(server);


        //Contact Events
        Contacts_Events.getMyContacts(server);
        Contacts_Events.oppContact(server);



        server.addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient socketIOClient) {
                //System.out.printf("con");
            }
        });

        server.start();
    }



}
