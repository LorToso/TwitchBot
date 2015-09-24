package irc.keywords;

public class Keyword {
	public static final String keywordPrefix = "!";
	
	private String keyword;

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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Keyword keyword1 = (Keyword) o;

		return keyword.equals(keyword1.keyword);

	}

	@Override
	public String toString(){
		return keyword;
	}
}
