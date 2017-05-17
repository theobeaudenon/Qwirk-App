package Server.Events.Contacts;

import Objet.Channel.Channel;
import Objet.Channel.ChannelUtils;
import Objet.Contacts.ContactUtils;
import Objet.User.User;
import Server.Data.Singleton_Data;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

import java.util.ArrayList;

/**
 * Server.Events.Contacts
 * Created by Theo on 16/05/2017 for app.
 */
public class Contacts_Events {

    public static void getMyContacts(SocketIOServer server) {

        server.addEventListener("getMyContacts", Integer.class, new DataListener<Integer>() {
            public void onData(SocketIOClient client, Integer idUser, AckRequest ackRequest) {
                // broadcast messages to all clients
                //server.getBroadcastOperations().sendEvent("chatevent", data);

                ArrayList<User> publicChannels = ContactUtils.getMyContacts(Singleton_Data.getInstance().getUserHashMap(),Singleton_Data.getInstance().getUserContactsHashMap(),idUser);

                if (ackRequest.isAckRequested()) {
                    // send ack response with data to client
                    ackRequest.sendAckData(publicChannels);
                }

                System.out.print("getMyContacts : "+publicChannels.size());
                // System.out.print(data.getPass());

            }
        });
    }



}