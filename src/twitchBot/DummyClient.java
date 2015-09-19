package twitchBot;

import org.jibble.pircbot.PircBot;

public class DummyClient extends PircBot {

	public DummyClient()
	{
		RandomString random = new RandomString(10);
		String name = random.nextString();
		setName(name);
		setLogin(name);
	}
}
