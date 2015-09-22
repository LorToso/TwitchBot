package irc.keywords;

public class Keyword {
	public static final String keywordPrefix = "!";
	
	String keyword;

	public Keyword(String keyword)
	{
		this.keyword = keywordPrefix + keyword;
	}
	
	@Override
	public int hashCode()
	{
		return keyword.hashCode();
	}
	@Override
	public String toString(){
		return keyword;
	}
}
