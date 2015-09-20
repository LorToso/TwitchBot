package irc;

import irc.IrcClient;
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
}
