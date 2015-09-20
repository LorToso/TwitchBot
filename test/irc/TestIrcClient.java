package irc;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import irc.IrcClient;

public class TestIrcClient {
	
	static String address = "localhost";
	static String username = "testuser1";
	static String pw = "testpw1";
	static int port = 6667;
	static String channel = "#test";
	static IrcClient ircClient;
	
	@Rule
	public TestWatcher watchman= new IrcClientTestWatcher();
	
	
	@BeforeClass
	public static void onlyOnce() throws IOException, InterruptedException
	{		
		IrcServer.start();
		ircClient = new IrcClient(username);
	}
	public static DummyClient addUser() throws NickAlreadyInUseException, IOException, IrcException
	{
		DummyClient user = new DummyClient();
		connectToServer(user);
		return user;
	}
	
	@Test
	public void connectToServer() throws NickAlreadyInUseException,  IrcException, IOException {
		try{
			connectToServer(ircClient);
		}
		catch(Exception e)
		{
			throw e;
		}finally
		{
			disconnectFromServer(ircClient);
		}
	}

	@Test
	public void connectToNotExistingServer() throws NickAlreadyInUseException,  IrcException {
		try{
			ircClient.connect("randomServer", port, pw);
			ircClient.joinChannel(channel);
		}
		catch(IOException ex){
			return;
		}
		finally
		{
			disconnectFromServer(ircClient);
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
		DummyMessagable m = new DummyMessagable(ircClient.getNick(), testmessage);
		dummy.addMessageListener(m);

		connectToServer(ircClient);
		
		ircClient.sendMessage(channel, testmessage);
		Thread.sleep(2000);
		
		disconnectFromServer(ircClient);
		disconnectFromServer(dummy);
		
		assertTrue(m.success);
	}	
	
	@Test
	public void sendWrongMessage() throws NickAlreadyInUseException, IOException, IrcException, InterruptedException
	{
		final String testmessage1 = "test message1";
		final String testmessage2 = "test message2";
		
		DummyClient dummy = addUser();
		DummyMessagable m = new DummyMessagable(ircClient.getNick(), testmessage1);
		dummy.addMessageListener(m);

		connectToServer(ircClient);
		
		ircClient.sendMessage(channel, testmessage2);
		
		Thread.sleep(2000);

		disconnectFromServer(ircClient);
		disconnectFromServer(dummy);
		
		assertFalse(m.success);
	}
	
	
	@Test
	public void receiveMessage() throws NickAlreadyInUseException, IOException, IrcException, InterruptedException
	{
		final String testmessage = "test message";
		
		connectToServer(ircClient);
		DummyClient user =  addUser();

		DummyMessagable m = new DummyMessagable(user.getNick(), testmessage);
		ircClient.addMessageListener(m);
				
		user.sendMessage(channel, testmessage);

		Thread.sleep(3000);
		
		ircClient.removeMessageListener(m);
		disconnectFromServer(ircClient);
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
		IrcServer.stop();
	}
}
