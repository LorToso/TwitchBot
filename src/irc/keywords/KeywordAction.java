package irc.keywords;

public abstract class KeywordAction {
	int actionPerformedCount = 0;
	
	public void act()
	{
		actionPerformedCount++;
		performAction();
	}
	protected abstract void performAction();
	
	public int getPerformedCount()
	{
		return actionPerformedCount;
	}
	public boolean wasPerformed()
	{
		return actionPerformedCount > 0;
	}
}
