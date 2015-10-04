package irc.keywords;

import java.util.List;

import irc.messages.Message;

public class Match {
	private Keyword matchedKeyword;
	private Message message;
	private List<Object> parameters;
	


	public Match(Keyword keyword, Message message, List<Object> parameters) {
		this.matchedKeyword = keyword;
		this.message = message;
		this.parameters = parameters;
	}


	public String getStringParameter(int index)
	{
		return (String)parameters.get(index);
	}
	public Integer getIntParameter(int index)
	{
		return (Integer)parameters.get(index);
	}
	public Double getDoubleParameter(int index)
	{
		return (Double)parameters.get(index);
	}
	public String getSender()
	{
		return message.sender;
	}

}
