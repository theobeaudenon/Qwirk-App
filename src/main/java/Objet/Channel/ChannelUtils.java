package Objet.Channel;

import Objet.LinkObjects.UserChannels;
import Objet.LinkObjects.UserContacts;
import Objet.Message.Message;
import Objet.User.User;
import Objet.User.UserUtils;
import Objet.Utils.Action;
import Server.Data.Singleton_Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

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


    public static ArrayList<Channel> getMyChannels(HashMap<Integer, Channel> channelHashMap, ArrayList<UserChannels> userChannelsHashMap, Integer data) {

        ArrayList<Channel> tmp = new ArrayList<Channel>();


        for (UserChannels integer : userChannelsHashMap) {
            if(data.equals(integer.getUserID()) ){
                Channel channelFromId = getChannelFromId(channelHashMap, integer.getChannelID());
                if(channelFromId!=null){
                    tmp.add(channelFromId);
                }

            }
        }

        return tmp;
    }

    public static Boolean operation(HashMap<Integer, Channel> channelHashMap, ArrayList<UserChannels> userChannelsHashMap, ChannelOpperation channelOpperation) {


        if(channelOpperation.getAction().equals(Action.AJOUTER)){

            Integer channelIncrement = Singleton_Data.getInstance().getChannelIncrement();
            channelOpperation.getChanneldata().setIdChannel(channelIncrement);
            channelHashMap.put(channelIncrement,channelOpperation.getChanneldata());
            userChannelsHashMap.add(new UserChannels(channelOpperation.getChanneldata().getIdCreator(),channelIncrement));
            return true;
        }




        return null;
    }
}
