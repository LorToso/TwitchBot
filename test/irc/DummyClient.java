package irc;

import irc.messages.Message;
import irc.messages.MessageListener;
import org.jibble.pircbot.IrcException;
import utilities.RandomString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DummyClient extends IrcClient implements MessageListener{
	
	static final int nameLength = 10;

    private List<Message> lastMessages;

	public DummyClient()
	{
        super(generateName());
        lastMessages = new ArrayList<>();
        addMessageListener(this);
	}
	private static String generateName()
	{
		RandomString random = new RandomString(nameLength);
        return "bot_" + random.nextString();
	}

	public static DummyClient addDummy(String address, String channel) throws IOException, IrcException
	{
		DummyClient user = new DummyClient();
		user.connect(address);
		user.joinChannel(channel);
		return user;
	}

    @Override
    public void onEvent(Message message) {
        lastMessages.add(message);
    }

    public boolean receivedMessage(String sender, String messageText)
    {
        if (lastMessages.size()==0)
            return false;

        Message lastMessage = lastMessages.get(lastMessages.size()-1);
        boolean correctMessage = lastMessage.message.equals(messageText);
        boolean correctSender = lastMessage.sender.equals(sender);
        return correctMessage && correctSender;

    }
}
