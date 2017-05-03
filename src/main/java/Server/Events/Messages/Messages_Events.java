package Server.Events.Messages;

import Objet.Message.Message;
import Objet.Message.MessageUtils;
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
public class Messages_Events {

    public static void newMessage(final SocketIOServer server){

        server.addEventListener("chatevent", Message.class, new DataListener<Message>() {
            public void onData(SocketIOClient client, Message data, AckRequest ackRequest) {


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
