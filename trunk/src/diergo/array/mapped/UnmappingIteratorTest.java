package diergo.array.mapped;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;


public class UnmappingIteratorTest
{
	@Test
	public void fieldsAreReturnedOnEmptyIterator()
	{
		Iterator<String[]> iterator = new UnmappingIterator(
				new String[] {"h1", "h2"}, Collections.<Map<String,String>>emptyList().iterator());
		assertTrue(iterator.hasNext());
		assertArrayEquals(new String[] {"h1", "h2"}, iterator.next());
	}
	
	@Test
	public void valuesAreReturnedFromSecondResult()
	{
		Map<String,String> values = new HashMap<String,String>();
		values.put("h1", "v1");
		values.put("h2", "v2");
		Iterator<String[]> iterator = new UnmappingIterator(
				new String[] {"h1", "h2"}, Collections.<Map<String,String>>singletonList(values).iterator());
		iterator.next();
		assertArrayEquals(new String[] {"v1", "v2"}, iterator.next());
	}
	
	@Test
	public void unknownValuesAreReturnedAsNull()
	{
		Map<String,String> values = new HashMap<String,String>();
		values.put("h1", "v1");
		Iterator<String[]> iterator = new UnmappingIterator(
				new String[] {"h1", "h2"}, Collections.<Map<String,String>>singletonList(values).iterator());
		iterator.next();
		assertArrayEquals(new String[] {"v1", null}, iterator.next());
	}
}
