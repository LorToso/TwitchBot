package irc.messages;

public class Notice  extends Entity{

	public String source;
	public String target;
	public String notice;
	public long timestamp;
	
	public String toString()
	{
		return "Notice from " + source + " to " + target + ": " + notice;
	}

}
