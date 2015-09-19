package twitchBot;

import utilities.RandomString;

public class DummyClient extends IrcClient{

	static final int nameLength = 10;
	
	public DummyClient()
	{
		String name = generateName();
		setName(name);
		setLogin(name);
	}
	private String generateName()
	{
		RandomString random = new RandomString(nameLength);
		String name = "bot_" + random.nextString();
		return name;
	}
}
