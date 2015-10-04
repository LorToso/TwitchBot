package irc.keywords;

import irc.*;
import org.jibble.pircbot.IrcException;
import org.junit.*;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class TestIrcKeywordDetector {
	final static String address = "localhost";
	final static String channel = "#test";
	
	static IrcClient client;
	static IrcKeywordDetector keywordDetector;
	
	@BeforeClass
	public static void beforeClass()
	{
		IrcServer.start();
	}
	@AfterClass
	public static void afterClass()
	{
		IrcServer.stop();
	}
	
	@Before
	public void before() throws IOException, IrcException
	{
		client = new IrcClient("testClient1");
		keywordDetector = new IrcKeywordDetector();
		client.connect(address);
		client.joinChannel(channel);
		keywordDetector.connect(client);
		sleep();
	}

	@After
	public void after()
	{
		keywordDetector.clearKeywords();
		keywordDetector.disconnect();
		client.disconnect();
		sleep();
	}

	private void sleep()
	{
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private void sleep2()
	{
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void connectToServer()
	{
		assertTrue(client.isConnected());
	}

	@Test
	public void addKeyword()
	{
		Keyword testKeyword = new Keyword("time");
		keywordDetector.addKeyword(testKeyword, new NoAction());
	}
	
	@Test
	public void keywordWithNoArguments() throws IOException, IrcException
	{
		final String keyString = "none";
		
		Keyword testKeyword = new Keyword(keyString);
		Action actionKeyword = new NoAction();
		keywordDetector.addKeyword(testKeyword, actionKeyword);
		
		DummyClient dummy = DummyClient.addDummy(address, channel);
		dummy.sendMessage(channel, Keyword.keywordPrefix + keyString);

		sleep2();
		assertTrue(actionKeyword.wasPerformed());
	}

	@Test
	public void keywordWithStringArgument() throws IOException, IrcException
	{
		final String keyString = "singleArgument";
		final String paramString = "testmessage";

		Keyword testKeyword = new Keyword(keyString,String.class);
		Action actionKeyword = new NoAction();
		keywordDetector.addKeyword(testKeyword, actionKeyword);

		DummyClient dummy = DummyClient.addDummy(address, channel);
		dummy.sendMessage(channel, Keyword.keywordPrefix + keyString + " " + paramString);

        //for (int i = 0; i < 10; i++)
		sleep2();
		assertTrue(actionKeyword.wasPerformed());
	}

    @Test
    public void echoTest() throws IOException, IrcException
    {
        final String paramString = "testmessage";

        Keyword testKeyword = new Echo();
        Action actionKeyword = new EchoAction(client);
        keywordDetector.addKeyword(testKeyword, actionKeyword);


        DummyMessagable messagable = new DummyMessagable(client.getName(), paramString);
        DummyClient dummy = DummyClient.addDummy(address, channel);
        dummy.addMessageListener(messagable);
        dummy.sendMessage(channel, testKeyword + " " + paramString);

        //for (int i = 0; i < 10; i++)
        sleep2();
        assertTrue(actionKeyword.wasPerformed());
        assertTrue(messagable.success);
    }
}
