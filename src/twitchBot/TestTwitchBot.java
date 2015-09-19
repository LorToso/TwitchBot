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
	static String ircServerPath = "C:\\Users\\Viaggatore\\Desktop\\Lorenzo\\Git\\Unreal3.2\\";
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
	public static DummyClient addUser()
	{
		DummyClient user = new DummyClient();
		try {
			user.connect(address);
		} catch (NickAlreadyInUseException e) {
			System.err.println("Nick " + user.getName() + " already in use.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (IrcException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
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
	public void connectToServer() throws NickAlreadyInUseException,  IrcException {
		try{
			twitchbot.connect(address, port, pw);
		}
		catch(IOException ex){
			fail("Unable to connect to the server");
		}
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

	
	@AfterClass
	public static void after()
	{
		ircServer.destroy();
	}
}
