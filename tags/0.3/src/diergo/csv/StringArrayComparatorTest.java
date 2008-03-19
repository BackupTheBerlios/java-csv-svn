package diergo.csv;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class StringArrayComparatorTest
{
	@Test
	public void simpleOrderingWorks()
	{
		StringArrayComparator comparator = new StringArrayComparator(new int[] {0});
		assertEquals(-1, comparator.compare(new String[] {"a"}, new String[] {"b"}));
	}

	@Test
	public void emptyArraysAreEqual()
	{
		StringArrayComparator comparator = new StringArrayComparator(new int[] {0});
		assertEquals(0, comparator.compare(new String[0], new String[0]));
	}

	@Test
	public void unusedFieldOrderIsIgnored()
	{
		StringArrayComparator comparator = new StringArrayComparator(new int[0]);
		assertEquals(0, comparator.compare(new String[] {"a"}, new String[] {"b"}));
	}
	
	@Test
	public void fieldOrderIsUsed()
	{
		StringArrayComparator comparator = new StringArrayComparator(new int[] {1});
		assertEquals(1, comparator.compare(new String[] {"a", "b"}, new String[] {"b", "a"}));
	}
	
	@Test
	public void numericOrdering()
	{
		StringArrayComparator comparator = new StringArrayComparator(new int[] {0}, new int[] {0});
		assertEquals(1, comparator.compare(new String[] {"10"}, new String[] {"5"}));
	}
}
