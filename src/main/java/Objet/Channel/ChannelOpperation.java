package Objet.Channel;

import Objet.Utils.Action;
import com.google.gson.Gson;

/**
 * Objet.Channel
 * Created by Theo on 17/05/2017 for app.
 */
public class ChannelOpperation {
    private Channel channeldata;
    private Integer channelid;
    private Action action;

    public ChannelOpperation(Integer channelid, Channel channeldata, Action action) {

        this.channelid = channelid;
        this.channeldata = channeldata;
        this.action = action;
    }

    public ChannelOpperation(String messageJson) {
        Gson gson = new Gson();
        ChannelOpperation grupoAplicacao = gson.fromJson(messageJson, ChannelOpperation.class);
        this.channeldata = grupoAplicacao.getChanneldata();
        this.channelid = grupoAplicacao.getChannelid();
        this.action = grupoAplicacao.getAction();

    }
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);

    }

    public Channel getChanneldata() {
        return channeldata;
    }

    public void setChanneldata(Channel channeldata) {
        this.channeldata = channeldata;
    }

    public Integer getChannelid() {
        return channelid;
    }

    public void setChannelid(Integer channelid) {
        this.channelid = channelid;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
