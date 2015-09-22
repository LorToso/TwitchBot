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
}
