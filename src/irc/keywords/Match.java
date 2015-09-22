package irc.keywords;

import java.util.List;

import irc.messages.Message;

public class Match {
	Keyword matchedKeyword;
	Message message;
	List<String> parameters;
	


	public Match(Keyword keyword, Message message, List<String> parameters) {
		this.matchedKeyword = keyword;
		this.message = message;	
		this.parameters = parameters;
	}


	public String getStringParameter(int index) throws NumberFormatException
	{
		return parameters.get(index);
	}
	public Integer getIntParameter(int index) throws NumberFormatException
	{
		return Integer.valueOf(parameters.get(index));
	}
	public Double getDoubleParameter(int index) throws NumberFormatException
	{
		return Double.valueOf(parameters.get(index));
	}

}
