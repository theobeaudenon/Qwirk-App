package Server.Events.Users;

import Objet.Channel.Channel;
import Objet.Channel.ChannelUtils;
import Objet.Contacts.ContactUtils;
import Objet.User.User;
import Objet.User.UserUtils;
import Server.Data.Singleton_Data;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

import java.util.ArrayList;

/**
 * Server.Events.Users
 * Created by Theo on 18/05/2017 for app.
 */
public class Users_Events {
    public static void getUserID(SocketIOServer server){

        server.addEventListener("getUserID", Integer.class, new DataListener<Integer>() {
            public void onData(SocketIOClient client, Integer idUser, AckRequest ackRequest) {

                User publicChannels = UserUtils.getUserFromId(Singleton_Data.getInstance().getUserHashMap(),idUser);

                if (ackRequest.isAckRequested()) {
                    // send ack response with data to client
                    ackRequest.sendAckData(publicChannels);
                }

                System.out.println("getUserID : "+publicChannels.getUserName());

            }
        });



    }
}
