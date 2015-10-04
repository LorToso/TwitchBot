package irc.keywords;

import irc.messages.Message;

import java.util.ArrayList;
import java.util.List;

public class KeywordMatcher {

	private static boolean hasCorrectKeywordFormat(Message message)
	{
		String messageString = message.message;
		
		boolean hasCorrectPrefix = messageString.startsWith(Keyword.keywordPrefix);
		
		int endOfKeyword = messageString.indexOf(' ');
		boolean isAValidLength = endOfKeyword == -1 || endOfKeyword > Keyword.keywordPrefix.length();
		
		return hasCorrectPrefix && isAValidLength;
	}

    public static Match match(Message message, Keyword keyword)
    {
        if(!hasCorrectKeywordFormat(message))
            return new NoMatch(keyword,message);

        String detectedKeyString = getWord(message, 0);

        if(!keyword.getKeyString().equals(detectedKeyString))
            return new NoMatch(keyword,message);

        List<Object> messageParameters = extractParameters(message,keyword);

        if(messageParameters==null)
            return new NoMatch(keyword,message);

        return new Match(keyword, message, messageParameters);
    }

    private static List<Object> extractParameters(Message message, Keyword keyword) {
        Class[] paramClasses = keyword.getParameterTypes();
        String[] words = message.message.split(" ");

        if(words.length-1 != paramClasses.length)
            return null;

        List<Object> allParams = new ArrayList<>();

        try
        {
            for (int i = 0; i < paramClasses.length; i++) {
                if (paramClasses[i] == String.class) {
                    allParams.add(words[i+1]);
                }
                else if(paramClasses[i] == Integer.class) {
                    allParams.add(Integer.valueOf(words[i+1]));
                }
                else if(paramClasses[i] == Double.class) {
                    allParams.add(Double.valueOf(words[i+1]));
                }
            }
        }
        catch (NumberFormatException e)
        {
            return null;
        }

        return allParams;
    }

    public static boolean doesMatch(Message message, Keyword keyword) {
        return !(match(message,keyword) instanceof NoMatch);
	}

    private static String getWord(Message message, int index){
        return message.message.split(" ")[index];
    }
}

