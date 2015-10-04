package irc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import irc.messages.MessageListener;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

import irc.messages.Message;
import utilities.RandomString;

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
