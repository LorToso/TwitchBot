package messages;

public class Join {
	public String channel;
	public String sender;
	public long timestamp;
	
	public String toString()
	{
		return sender + " added channel " + channel;
	}
}
