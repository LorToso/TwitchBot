package twitchBot;

import java.io.File;
import java.io.IOException;

import org.jibble.pircbot.IrcException;

import irc.IrcClient;
import irc.messages.Join;
import irc.messages.JoinListener;
import irc.messages.Message;
import irc.messages.MessageListener;
import irc.messages.Notice;
import irc.messages.NoticeListener;
import sqlite.SQLiteConnector;

public class IrcBot implements JoinListener, MessageListener, NoticeListener{
	IrcClient ircClient;
	SQLiteConnector sqlite;
	
	public IrcBot(String username) {
		try
		{
			SQLiteConnector.initDriver();	
		}
		catch(ClassNotFoundException e)
		{//TODO
		}
		
		sqlite = new SQLiteConnector(new File("TwitchBot.sql"));
		ircClient = new IrcClient(username);
		ircClient.addJoinListener(this);
		ircClient.addNoticeListener(this);
		ircClient.addMessageListener(this);	
	}
	public void connect(String address, String channel)
	{
		try {
			ircClient.connect(address);
			ircClient.joinChannel(channel);
		} catch (IOException | IrcException e) {
			e.printStackTrace();
		}
	}
	
	public void onEvent(Message message)
	{
		System.out.println(message);
	}
	public void onEvent(Join join)
	{
		sqlite.add(join);
		System.out.println(join);
	}
	public void onEvent(Notice notice)
	{
		System.out.println(notice);
	}
}
