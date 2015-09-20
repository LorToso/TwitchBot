package irc;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class IrcClientTestWatcher extends TestWatcher{
	@Override
	protected void failed(Throwable e, Description description) {
		sleep();
	}
	@Override
	protected void succeeded(Description description) {
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
}