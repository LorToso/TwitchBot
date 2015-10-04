package irc;

import irc.keywords.*;
import irc.messages.Message;
import irc.messages.MessageListener;

import java.util.HashMap;
import java.util.Map;

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
        Keyword detectedKeyword = new NoKeyword();
		for(Keyword keyword : allKeywords.keySet())
        {
            if(!KeywordMatcher.doesMatch(message, keyword))
                return;
            detectedKeyword = keyword;
        }

		Action action = getActionForKeyword(detectedKeyword);
		Match match = KeywordMatcher.match(message,detectedKeyword);
		
		action.act(match);
	}
	private Action getActionForKeyword(Keyword keyword)
	{
		Action action = allKeywords.get(keyword);
		return action!=null ? action : new NoAction();
	}

	public void addKeyword(Keyword keyword, Action actionKeyword) {
		allKeywords.put(keyword, actionKeyword);
	}
	public void clearKeywords()
	{
		allKeywords.clear();
	}

}
