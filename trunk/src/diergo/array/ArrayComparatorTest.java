package diergo.array;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class ArrayComparatorTest
{
	@Test
	public void simpleOrderingWorks()
	{
		ArrayComparator<String> comparator = new ArrayComparator<String>(new int[] {0});
		assertEquals(-1, comparator.compare(new String[] {"a"}, new String[] {"b"}));
	}

	@Test
	public void emptyArraysAreEqual()
	{
		ArrayComparator<String> comparator = new ArrayComparator<String>(new int[] {0});
		assertEquals(0, comparator.compare(new String[0], new String[0]));
	}

	@Test
	public void unusedFieldOrderIsIgnored()
	{
		ArrayComparator<String> comparator = new ArrayComparator<String>(new int[0]);
		assertEquals(0, comparator.compare(new String[] {"a"}, new String[] {"b"}));
	}
	
	@Test
	public void fieldOrderIsUsed()
	{
		ArrayComparator<String> comparator = new ArrayComparator<String>(new int[] {1});
		assertEquals(1, comparator.compare(new String[] {"a", "b"}, new String[] {"b", "a"}));
	}
}
