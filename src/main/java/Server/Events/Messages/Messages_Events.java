package Server.Events.Messages;

import Objet.Message.Message;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

/**
 * Created by theobeaudenon on 25/04/2017.
 */
public class Messages_Events {

    public static void newMessage(SocketIOServer server){

        server.addEventListener("chatevent", Message.class, new DataListener<Message>() {
            public void onData(SocketIOClient client, Message data, AckRequest ackRequest) {
                // broadcast messages to all clients
                //server.getBroadcastOperations().sendEvent("chatevent", data);
                System.out.print(data.getUserName());
                System.out.print(data.getMessage());

            }
        });

    }


}
