package irc;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import irc.keywords.Keyword;
import irc.keywords.Action;
import irc.keywords.Match;
import irc.keywords.NoAction;
import irc.keywords.NoKeyword;
import irc.keywords.KeywordMatcher;
import irc.messages.Message;
import irc.messages.MessageListener;

public class IrcKeywordDetector implements MessageListener{
	IrcClient client;
	String keywordPrefix;
	Map<Keyword,Action> allKeywords;
	
	public IrcKeywordDetector() {
		this("!");
	}

	public IrcKeywordDetector(String keywordPrefix) {
		this.keywordPrefix = keywordPrefix;
		this.allKeywords = new HashMap<>();
		
		this.allKeywords.put(new NoKeyword(), new NoAction());
	}
	public void connect(IrcClient client)
	{
		this.client = client;
		client.addMessageListener(this);		
	}
	public void disconnect()
	{
		client.removeMessageListener(this);	
		this.client = null;	
	}

	@Override
	public void onEvent(Message message) {
		Keyword keyword = KeywordMatcher.extractKeyword(message);
		
		Action action = getActionForKeyword(keyword);
		Match match = KeywordMatcher.match(keyword, message);
		
		action.act(match);
	}
	private Action getActionForKeyword(Keyword keyword)
	{
		return allKeywords.get(keyword);
	}

	public void addKeyword(Keyword keyword, Action actionKeyword) {
		allKeywords.put(keyword, actionKeyword);
	}
	public void clearKeywords()
	{
		allKeywords.clear();
	}

}
