package irc;

import irc.messages.Message;
import irc.messages.MessageListener;

public class TestMessagable implements MessageListener{
	public boolean success = false;
	String expectedUser;
	String expectedMessage;
	
	public TestMessagable(String user, String message) {
		this.expectedUser = user;
		this.expectedMessage = message;
	}
	
	@Override
	public void onEvent(Message message) {
		success = expectedUser.equals(message.sender) && expectedMessage.equals(message.message);
	}
}
