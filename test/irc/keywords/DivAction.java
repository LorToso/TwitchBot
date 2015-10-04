package irc.keywords;

import irc.IrcClient;

public class DivAction extends Action {
    private IrcClient client;

    public DivAction(IrcClient client)
    {
        this.client = client;
    }

    @Override
    protected void performAction(Match match) {
        float s1 = match.getFloatParameter(0);
        float s2 = match.getFloatParameter(1);
        float result = s1/s2;
        client.sendMessage(match.getSender(), Float.toString(result));
    }
}
