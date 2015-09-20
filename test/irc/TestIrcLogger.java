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
		DummyClient dummy = new DummyClient();
		dummy.connect(address);
		dummy.joinChannel(channel);
		
		Thread.sleep(2000);
		
		Join expectedJoin = new Join();
		expectedJoin.channel = channel;
		expectedJoin.sender = dummy.getName(); 
		
		logger.flush();
		
		List<String> fullFile = readCompleteLogFile();
		assertTrue(fullFile.size() > 3);
		assertEquals(expectedJoin.toString(),fullFile.get(3));
		dummy.disconnect();
	}
	
	
	@AfterClass
	public static void after()
	{
		IrcServer.stop();
	}
}
