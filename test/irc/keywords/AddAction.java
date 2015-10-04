package irc.keywords;

import irc.IrcClient;

public class AddAction extends Action {
    private IrcClient client;

    public AddAction(IrcClient client)
    {
        this.client = client;
    }
    @Override
    protected void performAction(Match match) {
        int s1 = match.getIntParameter(0);
        int s2 = match.getIntParameter(1);
        int result = s1+s2;
        client.sendMessage(match.getSender(), Integer.toString(result));
    }
}
