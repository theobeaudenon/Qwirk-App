package Objet.Channel;

import Objet.Message.Message;
import Objet.User.User;
import Objet.User.UserUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by theobeaudenon on 25/04/2017.
 */
public class ChannelUtils {

    public static ArrayList<Channel> getPublicChannels(HashMap<Integer, Channel> channelHashMap){

        ArrayList<Channel> tmp = new ArrayList<Channel>();
        for (Channel message:
                channelHashMap.values()) {
            if (message.ispublic()){
                tmp.add(message);
            }
        }
        return tmp;

    }


    public static Channel getChannelFromId(HashMap<Integer, Channel> channelHashMap, Integer data) {


         for (Channel message:
                channelHashMap.values()) {
            if (message.getIdChannel().equals(data)){
                return message;
            }
        }
        return null;

    }


    public static ArrayList<Channel> getMyChannels(HashMap<Integer, Channel> channelHashMap, HashMap<Integer, Integer> userChannelsHashMap, Integer data) {

        ArrayList<Channel> tmp = new ArrayList<Channel>();
        for (Integer integer : userChannelsHashMap.keySet()) {
            if(integer.equals(data)){
                Channel channelFromId = getChannelFromId(channelHashMap, userChannelsHashMap.get(integer));
                if(channelFromId!=null){
                    tmp.add(channelFromId);
                }

            }
        }

        return tmp;
    }

    public static Boolean operation(HashMap<Integer, Channel> channelHashMap, HashMap<Integer, Integer> userChannelsHashMap, ChannelOpperation channelOpperation) {



        return null;
    }
}
