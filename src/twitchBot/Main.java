package twitchBot;

import java.io.IOException;


import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

public class Main {
    public static void main(String[] args) throws NickAlreadyInUseException, IOException, IrcException, InterruptedException {
    	String address = "localhost";
    	String username = "lortoso";
    	String pw = "oauth:hzvtvjaca2fy6zrr2znolmbgr09mbi";
    	int port = 6667;
    	String channel = "#test";
    	
    	TwitchBot twitchbot = new TwitchBot(username);
    	twitchbot.setVerbose(true);
    	twitchbot.connect(address, port, pw);
    	twitchbot.joinChannel(channel);
    }
}
