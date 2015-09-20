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

public class TestIrcLogger {
	static File testLogFile = new File("testLog.log");
	static String address = "localhost";
	static IrcClient client;
	static IrcLogger logger;
	
	@Rule
	public TestWatcher watchman= new IrcLoggerTestWatcher(testLogFile);
	
	@BeforeClass
	public static void before()
	{
		IrcServer.start();
	}

	private IrcLogger connect() throws NickAlreadyInUseException, IOException, IrcException
	{
		client = new IrcClient("ircClient1");
		logger = new IrcLogger(testLogFile, client);
		logger.open();
		client.connect(address);
		return logger;
	}
	private void disconnect()
	{
		client.disconnect();
		logger.close();
	}
	
	@Test
	public void testConnectToServer() throws NickAlreadyInUseException, IOException, IrcException
	{
		connect();
		disconnect();
	}
	
	@Test
	public void fileIsBeingCreated() throws NickAlreadyInUseException, IOException, IrcException
	{
		connect();	
		assertTrue(testLogFile.exists());
		disconnect();
	}

	@Test
	public void fileIsEmpty() throws NickAlreadyInUseException, IOException, IrcException
	{
		connect();
		List<String> fullFile = readCompleteLogFile();
		assertTrue(fullFile.isEmpty());
		disconnect();
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
	
	@AfterClass
	public static void after()
	{
		IrcServer.stop();
	}
}
