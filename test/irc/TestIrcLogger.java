package irc;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import irc.messages.Join;
import irc.messages.Message;

public class TestIrcLogger {
	static File testLogFile = new File("testLog.log");
	static String address = "localhost";
	static String channel = "#test";
	static IrcClient client;
	static IrcLogger logger;
	
	@BeforeClass
	public static void beforeClass()
	{
		IrcServer.start();
	}
	@AfterClass
	public static void afterClass()
	{
		IrcServer.stop();
		testLogFile.delete();
	}
	
	@After
	public void after()
	{
		sleep();
		deleteLogFile();
		try {
			TestIrcLogger.disconnect();
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	@Before
	public void before()
	{
		try {
			TestIrcLogger.connect();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	

	private void sleep()
	{
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private void deleteLogFile()
	{
		if(testLogFile.exists())
			testLogFile.delete();
	}
	
	
	public static IrcLogger connect() throws IOException, IrcException, InterruptedException
	{
		client = new IrcClient("ircClient1");
		logger = new IrcLogger(testLogFile, client);
		logger.open();
		client.connect(address, channel);
		Thread.sleep(1000);
		return logger;
	}
	public static void disconnect() throws InterruptedException
	{
		client.disconnect();
		logger.close();
		Thread.sleep(1000);
	}
	
	@Test
	public void connectToServer() throws IOException, IrcException, InterruptedException
	{
		assertTrue(client.isConnected());
	}
	
	@Test
	public void fileIsBeingCreated() throws IOException, IrcException, InterruptedException
	{
		assertTrue(testLogFile.exists());
	}

	@Test
	public void fileIsEmpty() throws IOException, IrcException, InterruptedException
	{
		logger.flush();
		List<String> fullFile = readCompleteLogFile();

		fullFile.remove(0);
		fullFile.remove(0);
		fullFile.remove(0);
		
		assertEquals(0,fullFile.size());
		// Should be: Looking up hostname
		// Found hostname
		// client joined
	}

	private List<String> readCompleteLogFile() throws IOException
	{
		FileReader fReader = new FileReader(testLogFile);
		BufferedReader reader = new BufferedReader(fReader);
		
		List<String> file = new ArrayList<>();
		String line = reader.readLine();
		while(line != null)
		{
			file.add(line);
			line = reader.readLine();
		}
		reader.close();
		return file;
	}

	@Test
	public void clientJoins() throws IOException, IrcException, InterruptedException
	{
		DummyClient dummy = addDummy();
		Join expectedJoin = generateJoin(dummy.getName()); 
		
		waitForLog();

		List<String> fullFile = readCompleteLogFile();
		assertTrue(fullFile.size() > 3);
		assertEquals(expectedJoin.toString(),fullFile.get(3));
		dummy.disconnect();
	}

	@Test
	public void multipleClientJoin() throws IOException, IrcException, InterruptedException
	{
		DummyClient dummy1 = addDummy();
		DummyClient dummy2 = addDummy();
		
		Join expectedJoin1 = generateJoin(dummy1.getName());
		Join expectedJoin2 = generateJoin(dummy2.getName());
		
		waitForLog();
		
		List<String> fullFile = readCompleteLogFile();
		assertTrue(fullFile.size() >= 5);
		assertEquals(expectedJoin1.toString(),fullFile.get(3));
		assertEquals(expectedJoin2.toString(),fullFile.get(4));
		dummy1.disconnect();
		dummy2.disconnect();
	}	
	
	@Test
	public void logMessage() throws IOException, IrcException, InterruptedException
	{
		final String messageString = "test message";
		
		DummyClient dummy1 = addDummy();
		dummy1.sendMessage(channel, messageString);
		Message expectedMessage = generateMessage(dummy1.getName(), messageString);

		waitForLog();
		
		List<String> fullFile = readCompleteLogFile();
		assertTrue(fullFile.size() >= 5);
		assertEquals(expectedMessage.toString(),fullFile.get(4));
		dummy1.disconnect();
	}
	@Test
	public void logMultipleMessages() throws IOException, IrcException, InterruptedException
	{
		final String messageString1 = "test message 1";
		final String messageString2 = "test message 2";
		final String messageString3 = "test message 3";
		
		DummyClient dummy1 = addDummy();
		DummyClient dummy2 = addDummy();
		dummy1.sendMessage(channel, messageString1);
		dummy2.sendMessage(channel, messageString2);
		dummy1.sendMessage(channel, messageString3);

		Message expectedMessage1 = generateMessage(dummy1.getName(), messageString1);
		Message expectedMessage2 = generateMessage(dummy2.getName(), messageString2);
		Message expectedMessage3 = generateMessage(dummy1.getName(), messageString3);

		waitForLog();
		
		List<String> fullFile = readCompleteLogFile();
		assertEquals(expectedMessage1.toString(),fullFile.get(5));
		assertEquals(expectedMessage2.toString(),fullFile.get(6));
		assertEquals(expectedMessage3.toString(),fullFile.get(7));
		dummy1.disconnect();
		dummy2.disconnect();	
	}
	
	
	private Message generateMessage(String user, String message)
	{
		Message expectedMessage = new Message();
		expectedMessage.channel = channel;
		expectedMessage.message = message;
		expectedMessage.sender = user;
		return expectedMessage;
	}
	private Join generateJoin(String user)
	{
		Join expectedMessage = new Join();
		expectedMessage.channel = channel;
		expectedMessage.sender = user;
		return expectedMessage;
	}
	
	private DummyClient addDummy() throws IOException, IrcException
	{
		DummyClient dummy = new DummyClient();
		dummy.connect(address);
		dummy.joinChannel(channel);
		return dummy;
	}
	private void waitForLog()
	{
		try {
			Thread.sleep(2000);
			logger.flush();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void logNotice() throws IOException
	{
		waitForLog();
		List<String> fullFile = readCompleteLogFile();	
		String notice1 = fullFile.get(0);
		String notice2 = fullFile.get(1);

		assertTrue(notice1.startsWith("Notice"));
		assertTrue(notice2.startsWith("Notice"));
	}
	@Test
	public void completeLoggingExample() throws IOException, IrcException, InterruptedException
	{
		final String message1 = "Hey there!";
		final String message2 = "What up?";
		final String message3 = "I'm leaving.";
		final String message4 = "K, bye.";

		DummyClient dummy1 = addDummy();
		DummyClient dummy2 = addDummy();

		Join expectedJoin1 = generateJoin(dummy1.getName());
		Join expectedJoin2 = generateJoin(dummy2.getName());

		waitForLog();

		dummy1.sendMessage(channel, message1);
		Thread.sleep(200);
		dummy2.sendMessage(channel, message2);
		Thread.sleep(200);
		dummy1.sendMessage(channel, message3);
		Thread.sleep(200);
		dummy2.sendMessage(channel, message4);
		Thread.sleep(200);
		
		Message expectedMessage1 = generateMessage(dummy1.getName(), message1);
		Message expectedMessage2 = generateMessage(dummy2.getName(), message2);
		Message expectedMessage3 = generateMessage(dummy1.getName(), message3);
		Message expectedMessage4 = generateMessage(dummy2.getName(), message4);
		
		waitForLog();
		
		List<String> fullFile = readCompleteLogFile();
		
		assertTrue(fullFile.size()==9);
		assertTrue(fullFile.get(0).startsWith("Notice"));
		assertTrue(fullFile.get(1).startsWith("Notice"));
		assertEquals(expectedJoin1.toString(), fullFile.get(3));
		assertEquals(expectedJoin2.toString(), fullFile.get(4));
		assertEquals(expectedMessage1.toString(), fullFile.get(5));
		assertEquals(expectedMessage2.toString(), fullFile.get(6));
		assertEquals(expectedMessage3.toString(), fullFile.get(7));
		assertEquals(expectedMessage4.toString(), fullFile.get(8));
		
		dummy1.disconnect();
		dummy2.disconnect();
	}
}
