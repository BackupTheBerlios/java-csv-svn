package diergo.array;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

import diergo.array.test.MockArrayReader;

public class ArrayReaderIteratorTest
{
	@Test
	public void emptyReaderResultsInEmptyIterator()
	{
		ArrayReader<String> in = new MockArrayReader<String>(Arrays.asList(new String[0][]));
		assertFalse(new ArrayReaderIterator<String>(in).hasNext());
	}

	@Test(expected=NoSuchElementException.class)
	public void emptyReaderThrowsExceptionOnNext()
	{
		ArrayReader<String> in = new MockArrayReader<String>(Arrays.asList(new String[0][]));
		new ArrayReaderIterator<String>(in).next();
	}
	
	@Test
	public void linesFromReaderAreReturned()
	{
		ArrayReader<String> in = new MockArrayReader<String>(Arrays.asList(new String[][] {{"1a", "1b"}, {"2a", "2b"}}));
		Iterator<String[]> i = new ArrayReaderIterator<String>(in);
		assertTrue(i.hasNext());
		assertArrayEquals(new String[] {"1a", "1b"}, i.next());
		assertTrue(i.hasNext());
		assertArrayEquals(new String[] {"2a", "2b"}, i.next());
		assertFalse(i.hasNext());
	}
}
