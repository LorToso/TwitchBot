package irc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;

import irc.messages.Join;
import irc.messages.JoinListener;
import irc.messages.Message;
import irc.messages.MessageListener;
import irc.messages.Notice;
import irc.messages.NoticeListener;

public class IrcClient {
	private IrcClientImpl client;
	private List<MessageListener> 	messageListeners 	= new ArrayList<>();
	private List<JoinListener> 		joinListeners 		= new ArrayList<>();
	private List<NoticeListener> 	noticeListeners 	= new ArrayList<>();

	public IrcClient(String name){
		client = new IrcClientImpl();
		client.setLoginName(name);
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
	
	final protected void onMessage(String channel, String sender, String login, String hostname, String message) {
		Message packedMessage = new Message();
		packedMessage.channel = channel;
		packedMessage.sender = sender;
		packedMessage.message = message;
		packedMessage.timestamp = System.currentTimeMillis();

        try {
            for (MessageListener m : messageListeners)
                m.onEvent(packedMessage);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}	
	final protected void onJoin(String channel, String sender, String login, String hostname)
	{
		Join join = new Join();
		join.channel = channel;
		join.sender = sender;
		join.timestamp = System.currentTimeMillis();

		for(JoinListener m : joinListeners)
			m.onEvent(join);
	}
	final protected void onNotice(String sourceNick, String sourceLogin, String sourceHostname, String target, String notice)
	{
		Notice packedNotice = new Notice();
		packedNotice.source = sourceNick;
		packedNotice.target = target;
		packedNotice.notice = notice;
		packedNotice.timestamp = System.currentTimeMillis();

		for(NoticeListener m : noticeListeners)
			m.onEvent(packedNotice);
	}
    final protected void onPrivateMessage(String sender, String login, String hostname, String message)
    {
        Message packedMessage = new Message();
        packedMessage.sender = sender;
        packedMessage.channel = "private";
        packedMessage.message = message;
        packedMessage.timestamp = System.currentTimeMillis();

        for(MessageListener m : messageListeners)
            m.onEvent(packedMessage);
    }

	public void connect(String address, String channel) throws IOException, IrcException
	{
		connect(address);
		joinChannel(channel);
	}
}
