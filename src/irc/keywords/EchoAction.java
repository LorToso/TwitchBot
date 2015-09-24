package irc.keywords;

import irc.IrcClient;

public class EchoAction extends Action{
    private IrcClient client;

    public EchoAction(IrcClient client)
    {
        this.client = client;
    }

    @Override
    protected void performAction(Match match) {
        String echoMessage = match.getStringParameter(0);
        client.sendMessage(match.getSender(), echoMessage);
    }
}
