package irc;

import static org.junit.Assert.*;

import java.io.IOException;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
	public void before() throws NickAlreadyInUseException, IOException, IrcException
	{
		client = new IrcClient("testClient1");
		keywordDetector = new IrcKeywordDetector(client);
		client.connect(address);
		client.joinChannel(channel);
	}

	@After
	public void after()
	{
		client.disconnect();
		sleep();
	}

	private void sleep()
	{
		try {
			Thread.sleep(500);
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
		keywordDetector.addKeyword(testKeyword);
	}
	
	@Test
	public void keywordIsBeingDetected()
	{
		Keyword testKeyword = new Keyword("time");
		keywordDetector.addKeyword(testKeyword);
		fail();
	}
}
