package twitchBot;

import messages.Message;
import messages.MessageListener;

public class TestMessagable implements MessageListener{
	public boolean success = false;
	String expectedUser;
	String expectedMessage;
	
	public TestMessagable(String user, String message) {
		this.expectedUser = user;
		this.expectedMessage = message;
	}
	
	@Override
	public void onMessage(Message message) {
		success = expectedUser.equals(message.sender) && expectedMessage.equals(message.message);
	}
}
