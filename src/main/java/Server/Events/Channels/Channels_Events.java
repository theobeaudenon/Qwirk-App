package Server.Events.Channels;

import Objet.Alerte.Alerte;
import Objet.Channel.Channel;
import Objet.Channel.ChannelOpperation;
import Objet.Channel.ChannelUtils;
import Objet.LinkObjects.UserChannels;
import Objet.User.User;
import Objet.User.UserUtils;
import Server.Data.Singleton_Data;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

import java.util.ArrayList;

/**
 * Created by theobeaudenon on 25/04/2017.
 */
public class Channels_Events {

    /*
    Event : getPublicChannels
    Paramètres : Integer
    Return : ArrayList<Channel>
    Permet a un client de recuperer les channels public afin de s'y connecter
     */
    public static void getPublicChannels(SocketIOServer server){

        server.addEventListener("getPublicChannels", Integer.class, new DataListener<Integer>() {
            public void onData(SocketIOClient client, Integer idUser, AckRequest ackRequest) {
                // broadcast messages to all clients
                //server.getBroadcastOperations().sendEvent("chatevent", data);

                ArrayList<Channel> publicChannels = ChannelUtils.getPublicChannels(Singleton_Data.getInstance().getChannelHashMap());

                if (ackRequest.isAckRequested()) {
                    // send ack response with data to client
                    ackRequest.sendAckData(publicChannels);
                }

                System.out.print("getPublicChannels : "+publicChannels.size());
                // System.out.print(data.getPass());

            }
        });

    }

    /*
    Event : joinChannel
    Paramètres : UserChannels
    Return : Boolean
    Permet a un client de rejoindre un channel sur le serveur
     */
    public static void joinChannel(SocketIOServer server){

        server.addEventListener("joinChannel", UserChannels.class, new DataListener<UserChannels>() {
            public void onData(SocketIOClient client, UserChannels userChannels, AckRequest ackRequest) {

                Boolean publicChannels = ChannelUtils.joinChannel(Singleton_Data.getInstance().getUserChannelsHashMap(),userChannels);

                if (ackRequest.isAckRequested()) {
                    // send ack response with data to client
                    ackRequest.sendAckData(publicChannels);
                }

                System.out.print("joinChannel : "+publicChannels);
                // System.out.print(data.getPass());

            }
        });

    }

    /*
    Event : leaveChannel
    Paramètres : UserChannels
    Return : Boolean
    Permet a un client de quitter un channel sur le serveur
     */
    public static void leaveChannel(SocketIOServer server){

        server.addEventListener("leaveChannel", UserChannels.class, new DataListener<UserChannels>() {
            public void onData(SocketIOClient client, UserChannels userChannels, AckRequest ackRequest) {

                Boolean publicChannels = ChannelUtils.leaveChannel(Singleton_Data.getInstance().getUserChannelsHashMap(),userChannels);

                if (ackRequest.isAckRequested()) {
                    // send ack response with data to client
                    ackRequest.sendAckData(publicChannels);
                }

                System.out.print("leaveChannel : "+publicChannels);
                // System.out.print(data.getPass());

            }
        });

    }


    /*
    Event : inviteChannel
    Paramètres : UserChannels
    Return : Alerte
    Permet a un client d'inviter un autre client sur un channel
     */
    public static void inviteChannel(SocketIOServer server){

        server.addEventListener("inviteChannel", UserChannels.class, new DataListener<UserChannels>() {
            public void onData(SocketIOClient client, UserChannels userChannels, AckRequest ackRequest) {

                Boolean publicChannels = ChannelUtils.joinChannel(Singleton_Data.getInstance().getUserChannelsHashMap(),userChannels);
                if(publicChannels){
                    client.sendEvent("alerte",new Alerte("Bravo","Votre contact à bien été ajouté a votre groupe/Channel"));
                    server.getBroadcastOperations().sendEvent("inviteContactChannel", userChannels);

                }else {
                    client.sendEvent("alerte",new Alerte("Erreur","Votre contact n'a pas été ajouté ( Déjà dans le channel / Banni ) ..."));
                }




                System.out.print("leaveChannel : "+publicChannels);
                // System.out.print(data.getPass());

            }
        });

    }

    /*
    Event : getMyChannels
    Paramètres : Integer
    Return : ArrayList<Channel>
    Permet a un client de recuperer ses channels afin de s'y connecter
     */
    public static void getMyChannels(SocketIOServer server) {

        server.addEventListener("getMyChannels", Integer.class, new DataListener<Integer>() {
            public void onData(SocketIOClient client, Integer idUser, AckRequest ackRequest) {
                // broadcast messages to all clients
                //server.getBroadcastOperations().sendEvent("chatevent", data);

                ArrayList<Channel> publicChannels = ChannelUtils.getMyChannels(Singleton_Data.getInstance().getChannelHashMap(),Singleton_Data.getInstance().getUserChannelsHashMap(),idUser);

                if (ackRequest.isAckRequested()) {
                    // send ack response with data to client
                    ackRequest.sendAckData(publicChannels);
                }

                System.out.print("getMyChannels : "+publicChannels.size());
                // System.out.print(data.getPass());

            }
        });
    }
    /*
    Event : channelOpperation
    Paramètres : ChannelOpperation
    Return : Boolean
    Permet a un client d'effectuer une modification sur un channel
     */
    public static void channelOpperation(final SocketIOServer server) {

        server.addEventListener("channelOpperation", ChannelOpperation.class, new DataListener<ChannelOpperation>() {
            public void onData(SocketIOClient client, ChannelOpperation channelOpperation, AckRequest ackRequest) {
                // broadcast messages to all clients
                //server.getBroadcastOperations().sendEvent("chatevent", data);

                Boolean publicChannels = ChannelUtils.operation(Singleton_Data.getInstance().getChannelHashMap(),Singleton_Data.getInstance().getUserChannelsHashMap(),channelOpperation);

                if(ackRequest.isAckRequested()){
                    ackRequest.sendAckData(publicChannels);
                }

                System.out.print("channelOpperation : "+publicChannels);
                // System.out.print(data.getPass());

            }
        });
    }



}
