package irc;

import irc.messages.Join;
import irc.messages.JoinListener;
import irc.messages.Message;
import irc.messages.MessageListener;
import irc.messages.Notice;
import irc.messages.NoticeListener;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class IrcKeywordDetector implements MessageListener, JoinListener, NoticeListener {
	IrcClient client;
	String keywordPrefix;
	
	public IrcKeywordDetector(IrcClient client) {
		this("!", client);
	}

	public IrcKeywordDetector(String keywordPrefix, IrcClient client) {
		this.client = client;
		this.keywordPrefix = keywordPrefix;
	}

	@Override
	public void onEvent(Notice notice) {
		throw new NotImplementedException();
	}

	@Override
	public void onEvent(Join joiner) {
		throw new NotImplementedException();
	}

	@Override
	public void onEvent(Message message) {
		throw new NotImplementedException();
	}

	public void addKeyword(Keyword testKeyword) {
		// TODO Auto-generated method stub
		
	}

}
