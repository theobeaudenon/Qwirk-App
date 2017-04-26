package Objet.Message;

import Objet.Channel.Channel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by theobeaudenon on 24/04/2017.
 */
public class MessageUtils {

    public static Message getMessageFromId(HashMap<Integer, Message> messageHashMap , Integer idMessage){


        return messageHashMap.get(idMessage);

    }


    public static ArrayList<Message> getMessageFromChannel(HashMap<Integer, Message> messageHashMap , Channel channel){

        ArrayList<Message> messagestemp = new ArrayList<Message>();
        for (Message message:
                messageHashMap.values()) {
            if (message.getChannel().equals(channel.getIdChannel())){
                messagestemp.add(message);
            }
        }
        return messagestemp;

    }
    public static ArrayList<Message> getMessageFromChannel(HashMap<Integer, Message> messageHashMap , Integer channelid){

        ArrayList<Message> messagestemp = new ArrayList<Message>();
        for (Message message:
                messageHashMap.values()) {
            if (message.getChannel().equals(channelid)){
                messagestemp.add(message);
            }
        }
        return messagestemp;

    }



}
