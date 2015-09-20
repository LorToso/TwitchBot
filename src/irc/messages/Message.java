package irc.messages;

public class Message  extends Entity{
	public String message;
	public String sender;
	public String channel;
	public long timestamp;

	public String toString()
	{
		return sender + ": " + message;
	}
}
