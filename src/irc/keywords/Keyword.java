package irc.keywords;

import irc.messages.Message;

public class Keyword {
	String keyword;
	public Keyword(String keyword)
	{
		this.keyword = keyword;
	}
	public boolean hasMatchIn(Message message) {
		String keywordInMessage = message.message.split(" ")[0].substring(1);
		
		return keyword.equalsIgnoreCase(keywordInMessage);
	}
	
}
