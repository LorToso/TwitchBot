package twitchBot;

import irc.IrcClient;
import messages.Join;
import messages.JoinListener;
import messages.Message;
import messages.MessageListener;
import messages.Notice;
import messages.NoticeListener;

public class TwitchBot implements JoinListener, MessageListener, NoticeListener{
	IrcClient ircClient;
	
	public TwitchBot(String username) {
		ircClient = new IrcClient(username);
		ircClient.addJoinListener(this);
		ircClient.addNoticeListener(this);
		ircClient.addMessageListener(this);	
	}
	public void onMessage(Message message)
	{
		System.out.println(message);
	}
	public void onJoin(Join join)
	{
		System.out.println(join);
	}
	public void onNotice(Notice notice)
	{
		System.out.println(notice);
	}
	public void connect()
	{
	}
	public void disconnect()
	{
		ircClient.disconnect();
	}
}
