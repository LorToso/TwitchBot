package irc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import irc.messages.Join;
import irc.messages.JoinListener;
import irc.messages.Message;
import irc.messages.MessageListener;
import irc.messages.Notice;
import irc.messages.NoticeListener;

public class IrcLogger implements MessageListener, JoinListener, NoticeListener{
	IrcClient ircClient;
	File logFile;
	BufferedWriter bufferedWriter;
	boolean isOpen = false;
	
	public IrcLogger(File logFile, IrcClient ircClient)
	{
		ircClient.addJoinListener(this);
		ircClient.addMessageListener(this);
		ircClient.addNoticeListener(this);
		this.logFile = logFile;
		open();
	}

	@Override
	public void onEvent(Notice notice) {
		writeStringToFile(notice.toString());
	}

	@Override
	public void onEvent(Join joiner) {
		writeStringToFile(joiner.toString());
		
	}

	@Override
	public void onEvent(Message message) {
		writeStringToFile(message.toString());
		
	}
	public void writeStringToFile(String toWrite)
	{
		if(!isOpen)
			open();
		try {
			bufferedWriter.write(toWrite);
			bufferedWriter.newLine();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	public void open()
	{
		try {
			if (!logFile.exists()) {
				logFile.createNewFile();
			}
			FileWriter fw = new FileWriter(logFile.getAbsoluteFile());
			bufferedWriter= new BufferedWriter(fw);
			isOpen = true;
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	public void close()
	{
		try {
			bufferedWriter.close();
		} catch (IOException e) {
		}
		ircClient.removeJoinListener(this);
		ircClient.removeMessageListener(this);
		ircClient.removeNoticeListener(this);
		isOpen = false;
	}

}
