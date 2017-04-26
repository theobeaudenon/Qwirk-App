package Client.Component;

import Objet.Channel.Channel;
import javafx.scene.control.Label;

/**
 * Created by Boufle on 26/04/2017.
 */
public class Component_Label_Group extends Label {

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    protected Channel channel;

}
