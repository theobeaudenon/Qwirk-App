package Server.Events.Messages;

import Objet.Alerte.Alerte;
import Objet.Bot.ActionBot;
import Objet.Bot.Bot;
import Objet.Bot.Commandes;
import Objet.LinkObjects.BotChannel;
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


    public static void newMessage(final SocketIOServer server){

        server.addEventListener("chatevent", Message.class, new DataListener<Message>() {
            public void onData(SocketIOClient client, Message data, AckRequest ackRequest) {
                if(! data.getImage()){
                    if(data.getMessage() != null){
                        if(data.getMessage().charAt(0) == ':'){

                            String[] splits = data.getMessage().split(" ");
                            if(":invite".equals(splits[0])){

                                try {
                                    Integer integer = Integer.valueOf(splits[1]);
                                    Bot botArrayList = Singleton_Data.getInstance().getBotArrayList(integer);
                                    if(botArrayList != null){
                                        for (BotChannel entry : Singleton_Data.getInstance().getBotChannelHashMap()) {
                                           if(data.getChannel().equals(entry.getChannelID())){
                                               client.sendEvent("alerte",new Alerte("Erreur","Ce bot est déjà dans le channel"));
                                               return;
                                           }else {
                                               Singleton_Data.getInstance().getBotChannelHashMap().add(new BotChannel(integer,data.getChannel()));
                                               client.sendEvent("alerte",new Alerte("Succès","Le bot a bien été ajouté"));
                                               return;
                                           }
                                        }

                                        }else {
                                        client.sendEvent("alerte",new Alerte("Erreur","Ce bot n'existe pas"));
                                        return;
                                     }
                                }catch (Exception e){
                                    client.sendEvent("alerte",new Alerte("Erreur","Erreur dans l'ajout du bot"));
                                     return;
                                }




                            }









                             for (BotChannel entry : Singleton_Data.getInstance().getBotChannelHashMap()) {

                                 if(data.getChannel().equals(entry.getChannelID())  ){
                                     Integer value = entry.getBotID();
                                     Bot botArrayList = Singleton_Data.getInstance().getBotArrayList(value);
                                     for (Commandes commandes : botArrayList.getCommandes()) {
                                         String[] split = data.getMessage().split(" ");
                                         if(commandes.getValue().equals(split[0])){

                                             if(ActionBot.BAN.equals(commandes.getActionBot())){
                                                 client.sendEvent("alerte",new Alerte("Info",commandes.getMessage()));
                                                 System.out.printf("ban");
                                                 return;

                                             }
                                             if(ActionBot.MESSAGE.equals(commandes.getActionBot())){
                                                 //client.sendEvent("alerte",commandes.getMessage());

                                                 Integer newID = Singleton_Data.getInstance().getMessageIncrement();

                                                 Singleton_Data.getInstance().getMessageHashMap().put(newID,data);
                                                 data.setMessage(commandes.getMessage());
                                                 data.setUserName(botArrayList.getName());
                                                 data.setUser(-1);
                                                 // broadcast messages to all clients
                                                 server.getBroadcastOperations().sendEvent("newmessage", data);


                                                 System.out.printf("message");
                                                 return;

                                             }
                                             if(ActionBot.KICK.equals(commandes.getActionBot())){
                                                 client.sendEvent("alerte",new Alerte("Succès",commandes.getMessage()));
                                                 System.out.printf("kick");
                                                 return;

                                             }

                                         }
                                     }
                                 }
                             }


                            client.sendEvent("alerte",new Alerte("Erreur","commande Inconue"));

                            return;
                        }
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

                for (Message message : messageFromChannel) {
                   // message.setData(null);
                    client.sendEvent("newmessage",message);
                }

                if (ackRequest.isAckRequested()) {
                    // send ack response with data to client
                   // ackRequest.sendAckData(messageFromChannel.get(0));
                }

                System.out.println("channelMessages : "+messageFromChannel.size());
                // System.out.print(data.getPass());

            }
        });
    }

}
