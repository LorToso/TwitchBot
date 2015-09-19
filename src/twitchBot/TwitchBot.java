package twitchBot;


public class TwitchBot extends IrcClient{
	
	public TwitchBot(String username) {
		this.setName(username);
		this.setLogin(username);
		
	}
	
	protected void onConnect()
	{
		System.out.println("Successfully connected.");
	}
	protected void onMessage(String channel, String sender, String login, String hostname, String message)
	{
		System.out.println(sender + ": " + message);
		super.onMessage(channel, sender, login, hostname, message);
	}
	protected void onServerResponse(int code, String response)
	{
		//System.out.println("Server response: " + code + ": " + response);
	}
	protected void onJoin(String channel, String sender, String login, String hostname)
	{
		System.out.println("onJoin");
	}
	protected void onNotice(String sourceNick, String sourceLogin, String sourceHostname, String target, String notice)
	{
		System.out.println("onNotice: " + notice);
	}
}
