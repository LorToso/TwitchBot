package irc.messages;

public class Join extends Entity{
	public String channel;
	public String sender;
	public long timestamp;
	
	public String toString()
	{
		return sender + " added channel " + channel;
	}
}
