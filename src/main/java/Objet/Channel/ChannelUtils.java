package Objet.Channel;

import Objet.LinkObjects.BanChannel;
import Objet.LinkObjects.UserChannels;
import Objet.Utils.Action;
import Server.Data.Singleton_Data;

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
    public static Boolean inChannel( ArrayList<UserChannels> userChannelsHashMap, UserChannels userChannels) {
        for (UserChannels channels : userChannelsHashMap) {
            if(channels.getUserID() == userChannels.getUserID() && channels.getChannelID() == userChannels.getChannelID()){
                return true;
            }
        }
        return false;
    }

    public static UserChannels getuserchannelChannel(ArrayList<UserChannels> userChannelsHashMap, UserChannels userChannels) {
        for (UserChannels channels : userChannelsHashMap) {
            if(channels.getUserID() == userChannels.getUserID() && channels.getChannelID() == userChannels.getChannelID()){
                return channels;
            }
        }
        return null;
    }



    public static Boolean joinChannel(ArrayList<UserChannels> userChannelsHashMap, UserChannels userChannels) {

        if(!inChannel(userChannelsHashMap,userChannels)){
            for (BanChannel banChannel : Singleton_Data.getInstance().getBanChannelHashMap()) {
                if(banChannel.getBanID() == userChannels.getUserID() && userChannels.getChannelID() == banChannel.getChannelID()){
                    return false;
                }
            }

            userChannelsHashMap.add(userChannels);
            return true;
        }else {
            return false;
        }

    }

    public static Boolean leaveChannel(ArrayList<UserChannels> userChannelsHashMap, UserChannels userChannels) {

        if(inChannel(userChannelsHashMap,userChannels)){
            UserChannels userChannels1 = getuserchannelChannel(userChannelsHashMap, userChannels);
            userChannelsHashMap.remove(userChannels1);
            return true;
        }else {
            return false;
        }
    }
}
