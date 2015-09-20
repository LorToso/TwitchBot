package irc;

import java.io.File;


public class IrcServer {
	static String ircServerPath = "ircServer//";
	static String ircServerExecutable = "wircd.exe";
	static Process ircServer;
	
	public static void start() 
	{
		if(ircServer != null)
			stop();
		
		try {
			ProcessBuilder builder = new ProcessBuilder(ircServerPath + ircServerExecutable);		
			builder.directory(new File(ircServerPath));
	        builder.redirectErrorStream();
			ircServer = builder.start();
			Thread.sleep(2000);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void stop()
	{
		if(ircServer != null)
			ircServer.destroy();
		ircServer = null;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
