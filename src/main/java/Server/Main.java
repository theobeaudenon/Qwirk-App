package Server;

import Objet.Bot.ActionBot;
import Objet.Bot.Bot;
import Objet.Bot.Commandes;
import Objet.LinkObjects.BotChannel;
import Server.Data.Singleton_Data;
import Server.Events.Call.Call_Events;
import Server.Events.Channels.Channels_Events;
import Server.Events.Contacts.Contacts_Events;
import Server.Events.Login_Signup.Login_Signup_Events;
import Server.Events.Messages.Messages_Events;
import Server.Events.Users.Users_Events;
import Server.Saving.Saving;
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
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by theobeaudenon on 24/04/2017.
 */
public class Main {

    public static void main(String[] args) {

        //Creation du signleton de données
        Singleton_Data.getInstance();
        Singleton_Data singleton_data = Saving.deserialzeSingleton();
        if(singleton_data!= null){
            Singleton_Data.setData(singleton_data);
        }else {
            Saving.serializeSingleton();
        }

        Timer timer = new Timer();

        timer.schedule( new TimerTask() {
            public void run() {
                Saving.serializeSingleton();

            }
        }, 0, 25*1000);


        //Demarage du serveur
        Configuration config = new Configuration();
        config.setHostname("localhost");
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





        //USER EVENT
        Users_Events.getUserID(server);
        Users_Events.resetUserID(server);

        //LOGIN SIGNUP EVENTS
        Login_Signup_Events.socketLoginEvent(server);
        Login_Signup_Events.socketSingupEvent(server);


        //channels EVENT
        Channels_Events.getPublicChannels(server);
        Channels_Events.getMyChannels(server);
        Channels_Events.channelOpperation(server);
        Channels_Events.inviteChannel(server);
        Channels_Events.joinChannel(server);
        Channels_Events.leaveChannel(server);

        //messages Events
        Messages_Events.newMessage(server);
        Messages_Events.getAllmessagesFromChannel(server);

        //Call Events
        Call_Events.makeACall(server);
        Call_Events.acceptedCall(server);

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
