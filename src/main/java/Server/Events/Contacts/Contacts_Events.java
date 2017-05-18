package Server.Events.Contacts;

import Objet.Alerte.Alerte;
import Objet.Channel.Channel;
import Objet.Channel.ChannelUtils;
import Objet.Contacts.Contact;
import Objet.Contacts.ContactUtils;
import Objet.User.User;
import Objet.User.UserUtils;
import Objet.Utils.Tuple;
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

                System.out.println("getMyContacts : "+publicChannels.size());
                // System.out.print(data.getPass());

            }
        });
    }


    public static void oppContact(final SocketIOServer server) {

        server.addEventListener("oppContacts",  Contact.class, new DataListener<Contact>() {
            public void onData(SocketIOClient client, Contact contact, AckRequest ackRequest) {
                // broadcast messages to all clients
                //server.getBroadcastOperations().sendEvent("chatevent", data);

                Boolean publicChannels = ContactUtils.addMyContacts(Singleton_Data.getInstance().getUserHashMap(),Singleton_Data.getInstance().getUserContactsHashMap(),contact);

                if(publicChannels){
                    if(contact.getMail()!= null){
                        User user = UserUtils.getUserFromMail(Singleton_Data.getInstance().getUserHashMap(), contact.getMail());
                        contact.setIdUser2(user.getUserID());
                    }

                    //ackRequest.sendAckData(publicChannels);
                    server.getBroadcastOperations().sendEvent("contactupdate", contact);
                }else {
                    client.sendEvent("alerte",new Alerte("Erreur","Cet utilisateur n'existe pas / Est déjà votre contact"));
                }

                System.out.println("oppContacts : "+publicChannels);
                // System.out.print(data.getPass());

            }
        });
    }
}
