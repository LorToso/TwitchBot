package irc.keywords;

import irc.messages.Message;

public class NoMatch extends Match{

	public NoMatch(Keyword keyword, Message message) {
		super(null,null,null);
	}

}
