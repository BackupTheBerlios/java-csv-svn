package diergo.array.mapped;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.Test;


public class MappedArrayIteratorTest
{
	@Test(expected=NoSuchElementException.class)
	public void emptyIteratorWithoutHeaderDoesNotWotk()
	{
		MappedArrayIterator.createWithHeaders(Arrays.asList(new String[0][]).iterator());
	}

	@Test
	public void emptyIteratorIsStillEmpty()
	{
		Iterator<Map<String,Integer>> iterator =
			new MappedArrayIterator<Integer>(new String[0], Arrays.asList(new Integer[0][]).iterator());
		assertFalse(iterator.hasNext());
	}

	@Test(expected=NoSuchElementException.class)
	public void emptyIteratorNextRaisesError()
	{
		new MappedArrayIterator<Integer>(new String[0], Arrays.asList(new Integer[0][]).iterator()).next();
	}

	@Test
	public void headerOnlyIteratorIsStillEmpty()
	{
		Iterator<Map<String,String>> iterator =
			MappedArrayIterator.createWithHeaders(Arrays.asList(new String[][] {{"h1", "h2"}}).iterator());
		assertFalse(iterator.hasNext());
	}

	@Test(expected=NoSuchElementException.class)
	public void headerOnlyIteratorHandlesNext()
	{
		MappedArrayIterator.createWithHeaders(Arrays.asList(new String[][] {{"h1", "h2"}}).iterator()).next();
	}

	@Test
	public void valuesAreMapped()
	{
		Map<String,Integer> result = new MappedArrayIterator<Integer>(new String[] {"h1", "h2"},
				Arrays.asList(new Integer[][] {{new Integer(1), new Integer(2)}}).iterator()).next();
		assertEquals(1, result.get("h1").intValue());
		assertEquals(2, result.get("h2").intValue());
	}

	@Test
	public void valuesAreMappedAsNull()
	{
		Map<String,Integer> result = new MappedArrayIterator<Integer>(new String[] {"h1", "h2"},
				Arrays.asList(new Integer[][] {{new Integer(1)}}).iterator()).next();
		assertEquals(1, result.get("h1").intValue());
		assertNull(result.get("h2"));
	}
}
