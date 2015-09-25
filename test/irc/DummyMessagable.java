package irc;

import irc.messages.Message;
import irc.messages.MessageListener;

public class DummyMessagable implements MessageListener{
	public boolean success = false;
	String expectedUser;
	String expectedMessage;

	public Message receivedMessage;

	public DummyMessagable(String user, String message) {
		this.expectedUser = user;
		this.expectedMessage = message;
	}
	
	@Override
	public void onEvent(Message message) {
		receivedMessage = message;
		success = expectedUser.equals(message.sender) && expectedMessage.equals(message.message);
	}
}
