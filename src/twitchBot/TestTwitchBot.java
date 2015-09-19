package twitchBot;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestTwitchBot {
	static String ircServerPath = "ircServer//";
	static String ircServerExecutable = "wircd.exe";
	
	static String address = "localhost";
	static String username = "testuser1";
	static String pw = "testpw1";
	static int port = 6667;
	static String channel = "#test";
	static TwitchBot twitchbot;
	static Process ircServer;
	
	@BeforeClass
	public static void onlyOnce() throws IOException, InterruptedException
	{		
		startIRCServer();
		twitchbot = new TwitchBot(username);
	}
	public static DummyClient addUser() throws NickAlreadyInUseException, IOException, IrcException
	{
		DummyClient user = new DummyClient();
		user.connect(address);
		return user;
	}
	private static void startIRCServer() throws IOException, InterruptedException
	{
		ProcessBuilder builder = new ProcessBuilder(ircServerPath + ircServerExecutable);		
		builder.directory(new File(ircServerPath));
        builder.redirectErrorStream();
		ircServer = builder.start();
		Thread.sleep(2000);	
	}
	
	@Test
	public void connectToServer() throws NickAlreadyInUseException,  IrcException, IOException {
		twitchbot.connect(address, port, pw);
	}

	@Test
	public void connectToNotExistingServer() throws NickAlreadyInUseException,  IrcException {
		try{
			twitchbot.connect("randomServer", port, pw);
		}
		catch(IOException ex){
			return;
		}
		fail("Successfully connected to not existing server.");
	} 

	@Test
	public void addRandomUser()
	{
		try {
			addUser();
		} catch (IOException | IrcException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void validateDummyClientName() throws Exception
	{
		try {
			for(int i=0; i < 20; i++)
			{
				addUser();	
				System.out.println(i);
				Thread.sleep(500);
			}
		} catch (IOException | IrcException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void sendMessage()
	{
		twitchbot.sendMessage(channel, "test message");
	}
	@Test
	public void receiveMessage() throws NickAlreadyInUseException, IOException, IrcException
	{
		return;
		//DummyClient user =  addUser();
		//user.sendMessage(channel, "test message");
		//TODO
	}
	
	@AfterClass
	public static void after()
	{
		ircServer.destroy();
	}
}
