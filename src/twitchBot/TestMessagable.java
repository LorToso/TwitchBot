package twitchBot;

public class TestMessagable implements Messagable{
	public boolean success = false;
	String user;
	String message;
	
	public TestMessagable(String user, String message) {
		this.user = user;
		this.message = message;
	}
	
	@Override
	public void message(String sender, String recievedMessage) {
		success = user.equals(sender) && message.equals(recievedMessage);
	}
}
