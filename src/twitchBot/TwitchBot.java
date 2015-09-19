package twitchBot;

import org.jibble.pircbot.PircBot;

public class TwitchBot extends PircBot{

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
	}
	protected void onServerResponse(int code, String response)
	{
		System.out.println("Server response: " + code + ": " + response);
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
