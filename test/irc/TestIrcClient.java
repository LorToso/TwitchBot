package irc;

import org.jibble.pircbot.IrcException;
import org.junit.*;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestIrcClient {
	
	static String address = "localhost";
	static String username = "testuser1";
	static String pw = "testpw1";
	static int port = 6667;
	static String channel = "#test";
	static IrcClient ircClient;
	
	@BeforeClass
	public static void onlyOnce() throws IOException, InterruptedException
	{		
		IrcServer.start();
		ircClient = new IrcClient(username);
	}
	@AfterClass
	public static void afterClass()
	{
		IrcServer.stop();
	}

    @Before
    public void before() throws IOException, IrcException {
        ircClient.connect(address,channel);
    }

	@After
	public void after() throws InterruptedException {
        ircClient.disconnect();
        Thread.sleep(500);
	}
	
	
	@Test
	public void connectToServer() throws IrcException, IOException {
        assertTrue(ircClient.isConnected());
	}

	@Test
	public void connectToNotExistingServer() throws IrcException {
        ircClient.disconnect();
		try{
			ircClient.connect("randomServer", port, pw);
			ircClient.joinChannel(channel);
		}
		catch(IOException ex){
			return;
		}
		fail("Successfully connected to not existing server.");
	} 
	
	@Test
	public void validateDummyClientName() throws Exception
	{
		final int dummyCount = 5;
		
		ArrayList<DummyClient> list = new ArrayList<>();
		try {
			for(int i=0; i < dummyCount; i++)
			{
				DummyClient dummy = DummyClient.addDummy(address, channel);
				list.add(dummy);	
			}
		} catch (IOException | IrcException e) {
			e.printStackTrace();
			throw e;
		}

		list.forEach(irc.DummyClient::disconnect);
	}
	
	@Test
	public void sendMessage() throws IOException, IrcException, InterruptedException
	{
		final String testmessage = "test message";
		
		DummyClient dummy = DummyClient.addDummy(address, channel);
		DummyMessagable m = new DummyMessagable(ircClient.getNick(), testmessage);
		dummy.addMessageListener(m);

		ircClient.sendMessage(channel, testmessage);
		Thread.sleep(2000);

        dummy.disconnect();

		assertTrue(m.success);
	}	
	
	@Test
	public void sendWrongMessage() throws IOException, IrcException, InterruptedException
	{
		final String testmessage1 = "test message1";
		final String testmessage2 = "test message2";
		
		DummyClient dummy = DummyClient.addDummy(address, channel);
		DummyMessagable m = new DummyMessagable(ircClient.getNick(), testmessage1);
		dummy.addMessageListener(m);

		
		ircClient.sendMessage(channel, testmessage2);
		
		Thread.sleep(2000);

        dummy.disconnect();
		assertFalse(m.success);
	}
	
	
	@Test
	public void receiveMessage() throws IOException, IrcException, InterruptedException
	{
		final String testmessage = "test message";

		DummyClient dummy = DummyClient.addDummy(address, channel);

		DummyMessagable m = new DummyMessagable(dummy.getNick(), testmessage);
		ircClient.addMessageListener(m);
				
		dummy.sendMessage(channel, testmessage);

		Thread.sleep(3000);
		
		ircClient.removeMessageListener(m);

        dummy.disconnect();

		assertTrue(m.success);
	}

	@Test
    public void privateMessage() throws IOException, IrcException, InterruptedException {
        final String testmessage = "test message";

        DummyClient dummy = DummyClient.addDummy(address, channel);

        DummyMessagable messagable = new DummyMessagable(dummy.getName(),testmessage);
        dummy.addMessageListener(messagable);
        ircClient.sendMessage(dummy.getName(),testmessage);

        Thread.sleep(2000);

        assertEquals(messagable.receivedMessage.channel, "private");
        assertEquals(messagable.receivedMessage.message, testmessage);
        assertEquals(messagable.receivedMessage.sender, ircClient.getName());

        dummy.disconnect();
    }
	
}
