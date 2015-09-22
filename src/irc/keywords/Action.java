package irc.keywords;

public abstract class Action {
	boolean wasPerformed = false;
	
	public void act(Match match)
	{
		wasPerformed = true;
		performAction(match);
	}
	protected abstract void performAction(Match match);
	
	public boolean wasPerformed()
	{
		return wasPerformed;
	}
}
