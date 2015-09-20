package irc.messages;

public class Join extends Entity{
	public String channel;
	public String sender;
	public long timestamp;
	
	public String toString()
	{
		String def = getDefaultStringRepresentation();
		def = def.replaceAll("%s", sender);
		def = def.replaceAll("%c", channel);
		
		return def;
	}
	public static String getDefaultStringRepresentation()
	{
		return "%s joined channel %c";
	}
}
