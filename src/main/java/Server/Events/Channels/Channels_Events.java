package Server.Events.Channels;

import Objet.Channel.Channel;
import Objet.Channel.ChannelOpperation;
import Objet.Channel.ChannelUtils;
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
