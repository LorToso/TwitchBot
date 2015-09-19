package messages;

public class Message {
	public String message;
	public String sender;
	public String channel;
	public long timestamp;

	public String toString()
	{
		return sender + ": " + message;
	}
}
