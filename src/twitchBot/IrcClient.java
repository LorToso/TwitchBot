package twitchBot;

import java.util.ArrayList;
import java.util.List;

import org.jibble.pircbot.PircBot;

public abstract class IrcClient extends PircBot{
	List<Messagable> toNotify = new ArrayList<Messagable>();

	public void addMessagee(Messagable messagee)
	{
		toNotify.add(messagee);
	}
	public void removeMessagee(Messagable messagee)
	{
		toNotify.remove(messagee);
	}
	protected void onMessage(String channel, String sender, String login, String hostname, String message)
	{
		messageAll(sender, message);
	}
	private void messageAll(String login, String message) {
		for(Messagable m : toNotify)
			m.message(login, message);
	}

}
