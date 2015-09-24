package utilities;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestRandomString {

	@SuppressWarnings("unused")
	@Test
	public void createObjectPositiveLength()
	{
		RandomString rs = new RandomString(10);
	}
	@SuppressWarnings("unused")
	@Test
	public void createObjectNegativeLength()
	{
		try{
		RandomString rs = new RandomString(-10);
		}
		catch(IllegalArgumentException e)
		{
			// Do nothing
		}
	}
	@SuppressWarnings("unused")
	@Test
	public void createObjectZeroLength()
	{
		try{
		RandomString rs = new RandomString(0);
		}
		catch(IllegalArgumentException e) {
			// Do nothing
		}
	}
	@SuppressWarnings("unused")
	@Test
	public void generateString()
	{
		RandomString rs = new RandomString(10);
		String s = rs.nextString();
	}
	@Test
	public void stringHasCorrectLength()
	{
		final int iterations = 100;
		final int length = 10;
		RandomString rs = new RandomString(length);
		
		for(int i=0; i < iterations; i++)
		{
			String s = rs.nextString();
			assertTrue(s.length() == length);
		}
	}
	@Test
	public void stringsAreNotEqual()
	{
		final int iterations = 100;
		final int length = 1000;
		List<String> allStrings = new ArrayList<>();

        RandomString rs = new RandomString(length);
		
		for(int i=0; i < iterations; i++)
		{
			String s = rs.nextString();
			assertFalse(allStrings.contains(s));
			allStrings.add(s);
		}
	}
	@Test
	public void stringCanBeComparedInList()
	{
		List<String> allStrings = new ArrayList<>();
		String a = "test";
		String b = "test";
		allStrings.add(a);
		assertTrue(allStrings.contains(b));
	}
    @Test
    public void toStringEqualsNextString()
    {
        final int length = 10;
        RandomString rs = new RandomString(length);
        assertTrue(rs.toString().length()==length);
    }
}
