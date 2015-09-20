package twitchBot;

import java.io.IOException;


import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

import irc.IrcClient;
import irc.IrcServer;

public class Main {
    public static void main(String[] args) throws NickAlreadyInUseException, IOException, IrcException, InterruptedException {
    	String address = "localhost";
    	String username = "lortoso";
    	//String pw = "oauth:hzvtvjaca2fy6zrr2znolmbgr09mbi";
    	//int port = 6667;
    	String channel = "#test";
    	
    	IrcServer.start();
    	
    	IrcBot ircBot = new IrcBot(username);
    	ircBot.connect(address, channel);

    	IrcClient[] clients = {new IrcClient("bot1"),new IrcClient("bot2"),new IrcClient("bot3")};

    	for(IrcClient c : clients)
    	{
    		c.connect(address);
    	}
    	
    	IrcServer.stop();
    }
}
