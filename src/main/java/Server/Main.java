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
import com.corundumstudio.socketio.AckMode;
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

        /*
        Programation de la sauvegarde du serveur sur un fichier plat
         */
        Timer timer = new Timer();
        timer.schedule( new TimerTask() {
            public void run() {
                Saving.serializeSingleton();

            }
        }, 0, 25*1000);


        //Demarage du serveur
        Configuration config = new Configuration();
        config.setHostname("10.29.18.65");
        config.setPort(9092);
        config.setMaxFramePayloadLength(1024 * 1024 * 10);
        config.setMaxHttpContentLength(1024 * 1024 * 10);

        SocketIOServer server = new SocketIOServer(config);

        /*
        Definitions des events socket du serveur
         */


        //USER EVENT
        Users_Events.getUserID(server);
        Users_Events.resetUserID(server);
        Users_Events.updateUser(server);

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
        Call_Events.deniedCall(server);
        Call_Events.callFlux(server);
        Call_Events.audioCallFlux(server);

        //Contact Events
        Contacts_Events.getMyContacts(server);
        Contacts_Events.oppContact(server);


        //Démarage du serveur
        server.start();


    }



}
