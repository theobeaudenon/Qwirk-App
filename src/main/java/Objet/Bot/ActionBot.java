package Objet.Bot;

import java.io.Serializable;

/**
 * Objet.Bot
 * Created by Theo on 17/05/2017 for app.
 */
public enum  ActionBot implements Serializable {

    KICK("KICK"),
    MESSAGE("MESSAGE"),
    BAN("BAN");

    private final String text;

    /**
     * @param text
     */
    private ActionBot(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
