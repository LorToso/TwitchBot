package irc.keywords;

import java.util.List;

import irc.messages.Message;

public class Match {
	private Message message;
	private List<Object> parameters;
	


	public Match(Message message, List<Object> parameters) {
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
	public Float getFloatParameter(int index)
	{
		return (Float)parameters.get(index);
	}
	public String getSender()
	{
		return message.sender;
	}

}
