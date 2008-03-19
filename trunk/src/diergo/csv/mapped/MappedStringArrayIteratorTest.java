package diergo.csv.mapped;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.Test;


public class MappedStringArrayIteratorTest
{
	@Test
	public void emptyIteratorIsStillEmpty()
	{
		Iterator<Map<String,String>> iterator =
			new MappedStringArrayIterator(Arrays.asList(new String[0][]).iterator());
		assertFalse(iterator.hasNext());
	}

	@Test(expected=NoSuchElementException.class)
	public void emptyIteratorHandlesNext()
	{
		new MappedStringArrayIterator(Arrays.asList(new String[0][]).iterator()).next();
	}

	@Test
	public void headerOnlyIteratorIsStillEmpty()
	{
		Iterator<Map<String,String>> iterator =
			new MappedStringArrayIterator(Arrays.asList(new String[][] {{"h1", "h2"}}).iterator());
		assertFalse(iterator.hasNext());
	}

	@Test(expected=NoSuchElementException.class)
	public void headerOnlyIteratorHandlesNext()
	{
		new MappedStringArrayIterator(Arrays.asList(new String[][] {{"h1", "h2"}}).iterator()).next();
	}

	@Test
	public void valuesAreMapped()
	{
		Map<String,String> result = new MappedStringArrayIterator(
				Arrays.asList(new String[][] {{"h1", "h2"}, {"v1", "v2"}}).iterator()).next();
		assertEquals("v1", result.get("h1"));
		assertEquals("v2", result.get("h2"));
	}

	@Test
	public void valuesAreMappedAsNull()
	{
		Map<String,String> result = new MappedStringArrayIterator(
				Arrays.asList(new String[][] {{"h1", "h2"}, {"v1"}}).iterator()).next();
		assertEquals("v1", result.get("h1"));
		assertNull(result.get("h2"));
	}
}
