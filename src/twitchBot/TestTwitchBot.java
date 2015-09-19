package twitchBot;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

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
	
	@Rule
	public TestWatcher watchman= new TwitchTestWatcher();
	
	
	@BeforeClass
	public static void onlyOnce() throws IOException, InterruptedException
	{		
		startIRCServer();
		twitchbot = new TwitchBot(username);
	}
	public static DummyClient addUser() throws NickAlreadyInUseException, IOException, IrcException
	{
		DummyClient user = new DummyClient();
		connectToServer(user);
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
		try{
			connectToServer(twitchbot);
		}
		catch(Exception e)
		{
			throw e;
		}finally
		{
			disconnectFromServer(twitchbot);
		}
	}

	@Test
	public void connectToNotExistingServer() throws NickAlreadyInUseException,  IrcException {
		try{
			twitchbot.connect("randomServer", port, pw);
			twitchbot.joinChannel(channel);
		}
		catch(IOException ex){
			return;
		}
		finally
		{
			disconnectFromServer(twitchbot);
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
		final int dummyCount = 5;
		
		ArrayList<DummyClient> list = new ArrayList<>();
		try {
			for(int i=0; i < dummyCount; i++)
			{
				list.add(addUser());	
			}
		} catch (IOException | IrcException e) {
			e.printStackTrace();
			throw e;
		}
		
		for(DummyClient dummy : list)
			dummy.disconnect();
	}
	
	@Test
	public void sendMessage() throws NickAlreadyInUseException, IOException, IrcException, InterruptedException
	{
		final String testmessage = "test message";
		
		DummyClient dummy = addUser();
		TestMessagable m = new TestMessagable(twitchbot.getLogin(), testmessage);
		dummy.addMessagee(m);

		connectToServer(twitchbot);
		
		twitchbot.sendMessage(channel, testmessage);
		Thread.sleep(2000);
		
		disconnectFromServer(twitchbot);
		disconnectFromServer(dummy);
		
		assertTrue(m.success);
	}	
	
	@Test
	public void sendWrongMessage() throws NickAlreadyInUseException, IOException, IrcException, InterruptedException
	{
		final String testmessage1 = "test message1";
		final String testmessage2 = "test message2";
		
		DummyClient dummy = addUser();
		TestMessagable m = new TestMessagable(twitchbot.getLogin(), testmessage1);
		dummy.addMessagee(m);

		connectToServer(twitchbot);
		
		twitchbot.sendMessage(channel, testmessage2);
		
		Thread.sleep(2000);

		disconnectFromServer(twitchbot);
		disconnectFromServer(dummy);
		
		assertFalse(m.success);
	}
	
	
	@Test
	public void receiveMessage() throws NickAlreadyInUseException, IOException, IrcException, InterruptedException
	{
		final String testmessage = "test message";
		
		connectToServer(twitchbot);
		DummyClient user =  addUser();

		TestMessagable m = new TestMessagable(user.getLogin(), testmessage);
		twitchbot.addMessagee(m);
				
		user.sendMessage(channel, testmessage);

		Thread.sleep(3000);
		
		twitchbot.removeMessagee(m);
		disconnectFromServer(twitchbot);
		disconnectFromServer(user);

		assertTrue(m.success);
	}
	
	private static void connectToServer(IrcClient client) throws NickAlreadyInUseException, IOException, IrcException
	{
		client.connect(address);
		client.joinChannel(channel);
	}
	private static void disconnectFromServer(IrcClient client)
	{
		client.disconnect();
	}
	
	@AfterClass
	public static void after()
	{
		ircServer.destroy();
	}
}
