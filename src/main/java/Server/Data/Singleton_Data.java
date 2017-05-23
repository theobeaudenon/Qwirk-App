package Server.Data;

import Objet.Bot.ActionBot;
import Objet.Bot.Bot;
import Objet.Bot.Commandes;
import Objet.Channel.Channel;
import Objet.LinkObjects.BotChannel;
import Objet.LinkObjects.UserChannels;
import Objet.LinkObjects.UserContacts;
import Objet.Message.Message;
import Objet.User.User;
import Objet.Utils.Tuple;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by theobeaudenon on 25/04/2017.
 */
public class Singleton_Data implements Serializable{

    Integer messageIncrement = 0;
    Integer channelIncrement = 0;
    Integer botIncrement = 0;

    HashMap<Integer, Message> messageHashMap = new HashMap<Integer, Message>();
    HashMap<Integer, Channel> channelHashMap = new HashMap<Integer, Channel>();
    HashMap<Integer, User> userHashMap = new HashMap<Integer, User>();

    ArrayList<Bot> botArrayList = new ArrayList<Bot>();

    ArrayList<UserChannels> userChannelsHashMap = new ArrayList<UserChannels>();
    ArrayList<UserContacts> userContactsHashMap = new ArrayList<UserContacts>();
    ArrayList<BotChannel> botChannelHashMap = new ArrayList<BotChannel>();




    /** Instance unique non préinitialisée */
    private static Singleton_Data INSTANCE = null;

    /** Point d'accès pour l'instance unique du singleton */
    public static synchronized Singleton_Data getInstance()
    {
        if (INSTANCE == null)
        { 	INSTANCE = new Singleton_Data();
        }
        return INSTANCE;
    }

    public static void setData(Singleton_Data data){
        Singleton_Data instance = Singleton_Data.getInstance();
        instance.setBotArrayList(data.getBotArrayList());
        instance.setBotChannelHashMap(data.getBotChannelHashMap());
        instance.setBotIncrement(data.getBotIncrement());
        instance.setChannelHashMap(data.getChannelHashMap());
        instance.setChannelIncrement(data.getChannelIncrement());
        instance.setMessageHashMap(data.getMessageHashMap());
        instance.setUserChannelsHashMap(data.getUserChannelsHashMap());
        instance.setUserHashMap(data.getUserHashMap());

    }

    private Singleton_Data() {



        //VAleurs par defaut
        userHashMap.put(0,new User(0,"test2","test2@test","toto","reset"));
        userHashMap.put(1,new User(1,"test","test@test","test","reset"));
        userHashMap.put(2,new User(2,"test1","test1@test","test","reset"));

        Integer channelIncrement = getChannelIncrement();
        channelHashMap.put(channelIncrement,new Channel(channelIncrement,"toto","",1493132555,0,true));
        channelIncrement = getChannelIncrement();
        channelHashMap.put(channelIncrement,new Channel(channelIncrement,"tata","",1493132555,0,true));
        channelIncrement = getChannelIncrement();
        channelHashMap.put(channelIncrement,new Channel(channelIncrement,"titi","",1493132555,0,true));
        channelIncrement = getChannelIncrement();
        channelHashMap.put(channelIncrement,new Channel(channelIncrement,"tutu","",1493132595,0,true));
        channelIncrement = getChannelIncrement();
        channelHashMap.put(channelIncrement,new Channel(channelIncrement,"je suis chan","",1493132595,0,true));
        channelIncrement = getChannelIncrement();
        channelHashMap.put(channelIncrement,new Channel(channelIncrement,"botchan","",1493132595,0,true));

        messageHashMap.put(getMessageIncrement(),new Message("Théo","message de test",1493132555,2,1,false,null,null));
        messageHashMap.put(getMessageIncrement(),new Message("Théo","autre message de test",1493132999,2,1,false,null,null));
        messageHashMap.put(getMessageIncrement(),new Message("Théo","autre message de test",1493152999,2,1,false,null,null));
        messageHashMap.put(getMessageIncrement(),new Message("Théo","autre message de test",1493142999,2,1,false,null,null));

        userChannelsHashMap.add(new UserChannels(1,2));
        userChannelsHashMap.add(new UserChannels(1,3));
        userChannelsHashMap.add(new UserChannels(1,5));
        userChannelsHashMap.add(new UserChannels(1,6));
        userChannelsHashMap.add(new UserChannels(0,6));

        userContactsHashMap.add(new UserContacts(0,1));
        userContactsHashMap.add(new UserContacts(0,2));
        userContactsHashMap.add(new UserContacts(1,2));

        String bot1 = "{\n" +
                "  \"commandes\": [\n" +
                "    {\n" +
                "      \"action\": \"KICK\",\n" +
                "      \"cmd\": \":kick\",\n" +
                "      \"message\": \"Le marteau du ban a frappé\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"action\": \"BAN\",\n" +
                "      \"cmd\": \":ban\",\n" +
                "      \"message\": \"Le marteau du ban a frappé\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"action\": \"MESSAGE\",\n" +
                "      \"cmd\": \":bite\",\n" +
                "      \"message\": \"chatte\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"action\": \"MESSAGE\",\n" +
                "      \"cmd\": \":hey\",\n" +
                "      \"message\": \"Coucou\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"name\": \"nom du bot\"\n" +
                "}";

        try {
            JsonParser parser = new JsonParser();
            JsonObject rootObj = parser.parse(bot1).getAsJsonObject();

            ArrayList<Commandes> commandes = new ArrayList<Commandes>();

            JsonArray locObj = rootObj.getAsJsonArray("commandes");
            for (JsonElement pa : locObj) {
                JsonObject paymentObj = pa.getAsJsonObject();
                String     action     = paymentObj.get("action").getAsString();
                String     quoteid     = paymentObj.get("cmd").getAsString();
                String     message     = paymentObj.get("message").getAsString();
                ActionBot actionBot=null;
                if(ActionBot.BAN.toString().equals(action)){
                    actionBot = ActionBot.BAN;
                }
                if(ActionBot.MESSAGE.toString().equals(action)){
                    actionBot = ActionBot.MESSAGE;
                }
                if(ActionBot.KICK.toString().equals(action)){
                    actionBot = ActionBot.KICK;
                }
                if(action== null){
                    throw new Exception();
                }
                commandes.add(new Commandes(actionBot,quoteid,message));
            }
            Bot name = new Bot(commandes, rootObj.get("name").getAsString(),this.getBotIncrement());
            this.getBotArrayList().add(name);
            this.getBotChannelHashMap().add(new BotChannel(name.getIdBot(),6));

        }catch (Exception e){

        }

    }

    public ArrayList<UserChannels>  getUserChannelsHashMap() {
        return userChannelsHashMap;
    }

    public void setUserChannelsHashMap(ArrayList<UserChannels> userChannelsHashMap) {
        this.userChannelsHashMap = userChannelsHashMap;
    }

    public HashMap<Integer, Message> getMessageHashMap() {
        return messageHashMap;
    }

    public Integer getBotIncrement() {
        botIncrement++;
        return botIncrement;
    }

    public void setBotIncrement(Integer botIncrement) {
        this.botIncrement = botIncrement;
    }

    public Integer getMessageIncrement() {
        messageIncrement++;
        return messageIncrement;
    }

    public void setChannelIncrement(Integer channelIncrement) {
        this.channelIncrement = channelIncrement;
    }

    public Integer getChannelIncrement() {
        channelIncrement++;
        return channelIncrement;
    }

    public ArrayList<Bot> getBotArrayList() {
        return botArrayList;
    }

    public void setBotArrayList(ArrayList<Bot> botArrayList) {
        this.botArrayList = botArrayList;
    }

    public void setMessageHashMap(HashMap<Integer, Message> messageHashMap) {
        this.messageHashMap = messageHashMap;
    }

    public HashMap<Integer, Channel> getChannelHashMap() {
        return channelHashMap;
    }

    public void setChannelHashMap(HashMap<Integer, Channel> channelHashMap) {
        this.channelHashMap = channelHashMap;
    }

    public HashMap<Integer, User> getUserHashMap() {
        return userHashMap;
    }

    public void setUserHashMap(HashMap<Integer, User> userHashMap) {
        this.userHashMap = userHashMap;
    }

    public ArrayList<UserContacts> getUserContactsHashMap() {
        return userContactsHashMap;
    }

    public ArrayList<BotChannel> getBotChannelHashMap() {
        return botChannelHashMap;
    }

    public void setBotChannelHashMap(ArrayList<BotChannel> botChannelHashMap) {
        this.botChannelHashMap = botChannelHashMap;
    }

    public Bot getBotArrayList(Integer value) {
        for (Bot bot : botArrayList) {
            if(bot.getIdBot().equals(value)){
                return bot;
            }
        }

        return null;
    }


}
