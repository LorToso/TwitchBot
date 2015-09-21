package irc;

import java.util.HashMap;
import java.util.Map;

import irc.keywords.Keyword;
import irc.keywords.KeywordAction;
import irc.messages.Message;
import irc.messages.MessageListener;

public class IrcKeywordDetector implements MessageListener{
	IrcClient client;
	String keywordPrefix;
	Map<Keyword,KeywordAction> keywords;
	
	public IrcKeywordDetector(IrcClient client) {
		this("!", client);
	}

	public IrcKeywordDetector(String keywordPrefix, IrcClient client) {
		this.client = client;
		this.keywordPrefix = keywordPrefix;
		this.keywords = new HashMap<>();
		client.addMessageListener(this);
	}

	@Override
	public void onEvent(Message message) {
		for(Keyword keyword : keywords.keySet())
		{
			if(keyword.hasMatchIn(message))
				keywords.get(keyword).act();
		}
	}

	public void addKeyword(Keyword keyword, KeywordAction actionKeyword) {
		keywords.put(keyword, actionKeyword);
	}

}
