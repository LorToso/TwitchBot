package irc;

import java.io.IOException;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

import irc.IrcClient;
import irc.messages.Message;
import utilities.RandomString;

public class DummyClient extends IrcClient{
	
	static final int nameLength = 10;
	
	public DummyClient()
	{
		super(generateName());
	}
	private static String generateName()
	{
		RandomString random = new RandomString(nameLength);
		String name = "bot_" + random.nextString();
		return name;
	}
	
	public Message sendDummyMessage(String channel)
	{
		final String messageString = "DummyMessage";
		
		Message message = new Message();
		message.channel = channel;
		message.sender = getName();
		message.timestamp = System.currentTimeMillis();
		message.message = messageString;
		
		sendMessage(channel, messageString);
		return message;
	}
	
	public static DummyClient addDummy(String address, String channel) throws NickAlreadyInUseException, IOException, IrcException
	{
		DummyClient user = new DummyClient();
		user.connect(address);
		user.joinChannel(channel);
		return user;
	}
}
