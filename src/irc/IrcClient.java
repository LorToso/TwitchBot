package irc;

import java.util.ArrayList;
import java.util.List;

import org.jibble.pircbot.PircBot;

import messages.Join;
import messages.JoinListener;
import messages.Message;
import messages.MessageListener;
import messages.Notice;
import messages.NoticeListener;

public class IrcClient extends PircBot{
	private List<MessageListener> 	messageListeners 	= new ArrayList<MessageListener>();
	private List<JoinListener> 		joinListeners 		= new ArrayList<JoinListener>();
	private List<NoticeListener> 	noticeListeners 	= new ArrayList<NoticeListener>();

	public IrcClient(String name){
		setName(name);
		setLogin(name);
	}
	
	public void addMessageListener(MessageListener messagee)
	{
		messageListeners.add(messagee);
	}
	public void removeMessageListener(MessageListener messagee)
	{
		messageListeners.remove(messagee);
	}
	public void addJoinListener(JoinListener messagee)
	{
		joinListeners.add(messagee);
	}
	public void removeJoinListener(JoinListener messagee)
	{
		joinListeners.remove(messagee);
	}
	public void addNoticeListener(NoticeListener messagee)
	{
		noticeListeners.add(messagee);
	}
	public void removeNoticeListener(NoticeListener messagee)
	{
		noticeListeners.remove(messagee);
	}
	
	final protected void onMessage(String channel, String sender, String login, String hostname, String message)
	{
		Message packedMessage = new Message();
		packedMessage.channel = channel;
		packedMessage.sender = sender;
		packedMessage.message = message;
		packedMessage.timestamp = System.currentTimeMillis();

		for(MessageListener m : messageListeners)
			m.onMessage(packedMessage);
	}	
	final protected void onJoin(String channel, String sender, String login, String hostname)
	{
		Join join = new Join();
		join.channel = channel;
		join.sender = sender;
		join.timestamp = System.currentTimeMillis();

		for(JoinListener m : joinListeners)
			m.onJoin(join);
	}
	final protected void onNotice(String sourceNick, String sourceLogin, String sourceHostname, String target, String notice)
	{
		Notice packedNotice = new Notice();
		packedNotice.source = sourceNick;
		packedNotice.target = target;
		packedNotice.notice = notice;
		packedNotice.timestamp = System.currentTimeMillis();

		for(NoticeListener m : noticeListeners)
			m.onNotice(packedNotice);
	}
}
