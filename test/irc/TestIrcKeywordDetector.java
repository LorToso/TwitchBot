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
	}
	
	@Test
	public void addKeyword()
	{
		
	}
}
