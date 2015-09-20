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
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import irc.messages.Join;
import irc.messages.Message;

public class TestIrcLogger {
	static File testLogFile = new File("testLog.log");
	static String address = "localhost";
	static String channel = "#test";
	static IrcClient client;
	static IrcLogger logger;
	
	@Rule
	public TestWatcher watchman= new IrcLoggerTestWatcher(testLogFile);
	
	@BeforeClass
	public static void before()
	{
		IrcServer.start();
	}

	public static IrcLogger connect() throws NickAlreadyInUseException, IOException, IrcException, InterruptedException
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
	public void connectToServer() throws NickAlreadyInUseException, IOException, IrcException, InterruptedException
	{
		assertTrue(client.isConnected());
	}
	
	@Test
	public void fileIsBeingCreated() throws NickAlreadyInUseException, IOException, IrcException, InterruptedException
	{
		assertTrue(testLogFile.exists());
	}

	@Test
	public void fileIsEmpty() throws NickAlreadyInUseException, IOException, IrcException, InterruptedException
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
	
	private String readLine() throws IOException
	{
		FileReader fReader = new FileReader(testLogFile);
		BufferedReader reader = new BufferedReader(fReader);
		String line =reader.readLine();
		reader.close();
		return line;
	}

	private List<String> readCompleteLogFile() throws IOException
	{
		FileReader fReader = new FileReader(testLogFile);
		BufferedReader reader = new BufferedReader(fReader);
		
		List<String> file = new ArrayList<String>();
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
	public void clientJoins() throws NickAlreadyInUseException, IOException, IrcException, InterruptedException
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
	public void multipleClientJoin() throws NickAlreadyInUseException, IOException, IrcException, InterruptedException
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
	public void logMessage() throws NickAlreadyInUseException, IOException, IrcException, InterruptedException
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
	public void logMultipleMessages() throws NickAlreadyInUseException, IOException, IrcException, InterruptedException
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
	
	private DummyClient addDummy() throws NickAlreadyInUseException, IOException, IrcException
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
	
	@AfterClass
	public static void after()
	{
		IrcServer.stop();
		testLogFile.delete();
	}
}
