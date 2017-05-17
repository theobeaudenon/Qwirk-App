package Server.Events.Messages;

import Objet.Bot.ActionBot;
import Objet.Bot.Bot;
import Objet.Bot.Commandes;
import Objet.Message.Message;
import Objet.Message.MessageUtils;
import Objet.User.User;
import Objet.User.UserUtils;
import Server.Data.Singleton_Data;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.google.gson.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by theobeaudenon on 25/04/2017.
 */
public class Messages_Events {

    public static String bot1 = "{\n" +
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


    public static void newMessage(final SocketIOServer server){

        server.addEventListener("chatevent", Message.class, new DataListener<Message>() {
            public void onData(SocketIOClient client, Message data, AckRequest ackRequest) {
                if(! data.getImage()){
                    if(data.getMessage().charAt(0) == '/'){
                         for (Map.Entry<Integer, Integer> entry : Singleton_Data.getInstance().getBotChannelHashMap().entrySet()) {
                             if(entry.getKey().equals(data.getChannel())){
                                 Integer value = entry.getValue();
                                 Bot botArrayList = Singleton_Data.getInstance().getBotArrayList(value);
                                 for (Commandes commandes : botArrayList.getCommandes()) {
                                     String[] split = data.getMessage().split(" ");
                                     if(commandes.getValue().equals(split[0])){

                                         if(ActionBot.BAN.equals(commandes.getActionBot())){
                                             client.sendEvent("alerte",commandes.getMessage());

                                         }
                                         if(ActionBot.MESSAGE.equals(commandes.getActionBot())){
                                             client.sendEvent("alerte",commandes.getMessage());
                                         }
                                         if(ActionBot.KICK.equals(commandes.getActionBot())){
                                             client.sendEvent("alerte",commandes.getMessage());

                                         }

                                     }
                                 }
                             }
                         }


                        client.sendEvent("alerte","commande");

                        return;
                    }
                }

                System.out.println("chatevent : "+data.getUserName()+" "+data.getMessage());


                Integer newID = Singleton_Data.getInstance().getMessageIncrement();

                Singleton_Data.getInstance().getMessageHashMap().put(newID,data);
                // broadcast messages to all clients

                System.out.println("newmessage : "+data.getMessage());
                server.getBroadcastOperations().sendEvent("newmessage", data);
                //System.out.print(data.getUserName());
                //System.out.print(data.getMessage());

            }
        });




    }


    public static void getAllmessagesFromChannel(SocketIOServer server) {
        server.addEventListener("channelMessages", Integer.class, new DataListener<Integer>() {
            public void onData(SocketIOClient client, Integer channelID, AckRequest ackRequest) {
                // broadcast messages to all clients
                //server.getBroadcastOperations().sendEvent("chatevent", data);

                ArrayList<Message> messageFromChannel = MessageUtils.getMessageFromChannel(Singleton_Data.getInstance().getMessageHashMap(), channelID);

                if (ackRequest.isAckRequested()) {
                    // send ack response with data to client
                    ackRequest.sendAckData(messageFromChannel);
                }

                System.out.println("channelMessages : "+messageFromChannel.size());
                // System.out.print(data.getPass());

            }
        });
    }

}
