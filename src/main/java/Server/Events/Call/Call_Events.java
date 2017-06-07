package Server.Events.Call;

import Objet.Alerte.Call;
import Objet.Call.CallData;
import Objet.Channel.Channel;
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
 * Server.Events.Call
 * Created by Theo on 22/05/2017 for app.
 */
public class Call_Events {

    /*
      Event : makeACall
      Paramètres : Call
      Broadcast : Call
      Permet a un client d'appeler
   */
    public static void makeACall(SocketIOServer server){

        server.addEventListener("makeACall", Call.class, new DataListener<Call>() {
            public void onData(SocketIOClient client, Call idUser, AckRequest ackRequest) {
                // broadcast messages to all clients
                //server.getBroadcastOperations().sendEvent("chatevent", data);

                idUser.setCallerUUID(client.getSessionId());
                idUser.setUserCalled( UserUtils.getUserFromId(Singleton_Data.getInstance().getUserHashMap(), idUser.getCalled()));
                idUser.setUserCaller( UserUtils.getUserFromId(Singleton_Data.getInstance().getUserHashMap(), idUser.getCaller()));


                if (ackRequest.isAckRequested()) {
                    // send ack response with data to client
                    // ackRequest.sendAckData(publicChannels);

                }
                server.getBroadcastOperations().sendEvent("incommingCall", idUser);

                System.out.print("MakeaCall : "+idUser.getCaller() +" " + idUser.getCalled());
                // System.out.print(data.getPass());

            }
        });

    }

    /*
      Event : acceptedCall
      Paramètres : Call
      Broadcast : Call
      Permet a un client d'accepter l'appel
   */
    public static void acceptedCall(SocketIOServer server){

        server.addEventListener("acceptedCall", Call.class, new DataListener<Call>() {
            public void onData(SocketIOClient client, Call idUser, AckRequest ackRequest) {
                idUser.setCalledUUID(client.getSessionId());

                server.getBroadcastOperations().sendEvent("callprosses", idUser);

                System.out.print("acceptedCall : "+idUser.getCaller() +" " + idUser.getCalled());
                // System.out.print(data.getPass());

            }
        });

    }

    /*
      Event : deniedCall
      Paramètres : Call
      Broadcast : Call
      Permet a un client de refuser l'appel
   */
    public static void deniedCall(SocketIOServer server){

        server.addEventListener("deniedCall", Call.class, new DataListener<Call>() {
            public void onData(SocketIOClient client, Call idUser, AckRequest ackRequest) {

                server.getBroadcastOperations().sendEvent("deniedCallBack", idUser);

                System.out.print("deniedCall : "+idUser.getCaller() +" " + idUser.getCalled());
                // System.out.print(data.getPass());

            }
        });

    }


    /*
      Event : callFlux
      Paramètres : CallData
      return : callInboud : data
      Permet a envoyer les données de l'appel ( Vidéo ) a l'autre
   */
    public static void callFlux(SocketIOServer server){

        server.addEventListener("callFlux", CallData.class, new DataListener<CallData>() {
            public void onData(SocketIOClient client, CallData idUser, AckRequest ackRequest) {

                if(client.getSessionId().equals(idUser.getCalledUUID())){
                    server.getClient(idUser.getCallerUUID()).sendEvent("callInboud", idUser.getData());

                }else{
                    server.getClient(idUser.getCalledUUID()).sendEvent("callInboud", idUser.getData());

                }
                //server.getBroadcastOperations().sendEvent("deniedCallBack", idUser);

                //System.out.print("deniedCall : "+idUser.getCaller() +" " + idUser.getCalled());
                // System.out.print(data.getPass());

            }
        });

    }


    /*
      Event : audioCallFlux
      Paramètres : CallData
      return : audioCallInboud : data
      Permet a envoyer les données de l'appel ( audio ) a l'autre
   */
    public static void audioCallFlux(SocketIOServer server){

        server.addEventListener("audioCallFlux", CallData.class, new DataListener<CallData>() {
            public void onData(SocketIOClient client, CallData idUser, AckRequest ackRequest) {

                if(client.getSessionId().equals(idUser.getCalledUUID())){
                    server.getClient(idUser.getCallerUUID()).sendEvent("audioCallInboud", idUser.getData());

                }else{
                    server.getClient(idUser.getCalledUUID()).sendEvent("audioCallInboud", idUser.getData());

                }
                //server.getBroadcastOperations().sendEvent("deniedCallBack", idUser);

                //System.out.print("deniedCall : "+idUser.getCaller() +" " + idUser.getCalled());
                // System.out.print(data.getPass());

            }
        });

    }


}
