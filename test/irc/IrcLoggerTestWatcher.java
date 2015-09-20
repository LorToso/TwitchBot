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
	protected void starting(Description description) {
		super.starting(description);

		try {
			TestIrcLogger.connect();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	protected void finished(Description description) {
		super.finished(description);
		sleep();
		deleteLogFile();
		
		try {
			TestIrcLogger.disconnect();
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	};
	
	@Override
	protected void failed(Throwable e, Description description) {
	}
	@Override
	protected void succeeded(Description description) {
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
