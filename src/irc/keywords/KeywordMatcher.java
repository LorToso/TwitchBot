package irc.keywords;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import irc.messages.Message;

public class KeywordMatcher {
	public static Match match(Keyword keyword, Message message)
	{
		List<String> parameters = splitToParameters(message);
		Match match = new Match(keyword, message, parameters);
		return match;
	}

	private static List<String> splitToParameters(Message message) {
		List<String> parameters = new ArrayList<>();
		String[] allParameters = message.message.split(" ");

		Collections.addAll(parameters, allParameters);
		return parameters;
	}
	
	public static boolean hasCorrectKeywordFormat(Message message)
	{
		String messageString = message.message;
		
		boolean hasCorrectPrefix = messageString.startsWith(Keyword.keywordPrefix);
		
		int endOfKeyword = messageString.indexOf(' ');
		boolean isAValidLength = endOfKeyword == -1 || endOfKeyword > Keyword.keywordPrefix.length();
		
		return hasCorrectPrefix && isAValidLength;
	}

	public static Keyword extractKeyword(Message message) {
		if(!hasCorrectKeywordFormat(message))
			return new NoKeyword();

		return splitKeyword(message);
	}
	private static Keyword splitKeyword(Message message)
	{
		int endOfKeyword = message.message.indexOf(' ');
		int startOfKeyword = Keyword.keywordPrefix.length();
		
		if(endOfKeyword == -1)
			endOfKeyword = message.message.length();
		
		String keywordString = message.message.substring(startOfKeyword, endOfKeyword);
        return new Keyword(keywordString);
	}
	
}

