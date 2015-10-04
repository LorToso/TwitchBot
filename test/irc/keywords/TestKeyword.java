package irc.keywords;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

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
			new Keyword("this test");
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
            new Keyword("");
        }
        catch(IllegalArgumentException e)
        {
            return;
        }
        fail();
    }
    @Test
    public void illegalSymbol()
    {
        try {
            new Keyword("!test");
        }
        catch(IllegalArgumentException e)
        {
            return;
        }
        fail();
    }
    @Test
    public void toStringTest()
    {
        final String testString = "test";
        Keyword k = new Keyword(testString);
        assertTrue(k.toString().equals(Keyword.keywordPrefix + testString));
    }
    @Test
    public void equalsDifferentObject()
    {
        final String testString = "test";
        Keyword k = new Keyword(testString);
        //noinspection EqualsBetweenInconvertibleTypes
        assertFalse(k.equals(new Integer(5)));
    }
    @Test
    public void equalsNull()
    {
        final String testString = "test";
        Keyword k = new Keyword(testString);
        //noinspection ObjectEqualsNull
        assertFalse(k.equals(null));
    }
    @Test
    public void equalsSelf()
    {
        final String testString = "test";
        Keyword k = new Keyword(testString);
        //noinspection EqualsWithItself
        assertTrue(k.equals(k));
    }
    @Test
    public void createWithOneParameter()
    {
        final String testString = "test";
        new Keyword(testString, String.class);
    }
    @Test
    public void createWithMultipleParameters()
    {
        final String testString = "test";
        new Keyword(testString, String.class, Integer.class, Float.class, String.class);
    }
    @Test
    public void createWithInvalidParameterClass()
    {
        final String testString = "test";
        try {
            new Keyword(testString, List.class);
        }
        catch (IllegalArgumentException e)
        {
            return;
        }
        fail();
    }
    @Test
    public void getParameterList()
    {
        final String testString = "test";
        final Class[] parameterList = new Class[]{Integer.class, String.class};
        Keyword k = new Keyword(testString, parameterList);
        assertArrayEquals(k.getParameterTypes(),parameterList);
    }
}
