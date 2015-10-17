package irc;

import org.jibble.pircbot.PircBot;

class IrcClientImpl extends PircBot {
    public void setLoginName(String name)
    {
        super.setName(name);
        super.setLogin(name);
    }
}
