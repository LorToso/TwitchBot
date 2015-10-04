package irc.keywords;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestKeyword {

	@Test
	public void keyWordsHaveTheSameHash()
	{
		Keyword keyword1 = new Keyword("test");
		Keyword keyword2 = new Keyword("test");
		assertEquals(keyword1.hashCode(), keyword2.hashCode());
	}
	@Test
	public void subTypesHaveTheSameHash()
	{
		Keyword keyword1 = new Keyword("echo");
		Keyword keyword2 = new Echo();
		assertEquals(keyword1.hashCode(), keyword2.hashCode());
	}
	@Test
	public void keyWordsEqual()
	{
		Keyword keyword1 = new Keyword("test");
		Keyword keyword2 = new Keyword("test");
		assertEquals(keyword1, keyword2);
	}
	@Test
	public void subTypesEqual()
	{
		Keyword keyword1 = new Keyword("echo");
		Keyword keyword2 = new Echo();
		assertEquals(keyword1, keyword2);
	}
	@Test
	public void twoWords()
	{
		try {
			Keyword keyword1 = new Keyword("this test");
		}
		catch(IllegalArgumentException e)
		{
			return;
		}
		fail();
	}
	@Test
	public void noKeyword()
	{
		try {
			Keyword keyword1 = new Keyword("");
		}
		catch(IllegalArgumentException e)
		{
			return;
		}
		fail();
	}
}
