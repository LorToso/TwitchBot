package irc;

import java.io.File;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class IrcLoggerTestWatcher extends TestWatcher{
	File logFile;
	public IrcLoggerTestWatcher(File logFile)
	{
		this.logFile = logFile;
	}
	@Override
	protected void failed(Throwable e, Description description) {
		sleep();
		deleteLogFile();
	}
	@Override
	protected void succeeded(Description description) {
		sleep();
		deleteLogFile();
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
		if(logFile.exists())
			logFile.delete();
	}
}
