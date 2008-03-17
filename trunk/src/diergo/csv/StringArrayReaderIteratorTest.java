package diergo.csv;

import java.io.EOFException;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;
import static org.junit.Assert.*;

public class StringArrayReaderIteratorTest
{
	@Test
	public void emptyReaderResultsInEmptyIterator()
	{
		StringArrayReader in = new TestReader(new String[0][]);
		assertFalse(new StringArrayReaderIterator(in).hasNext());
	}

	@Test(expected=NoSuchElementException.class)
	public void emptyReaderThrowsExceptionOnNext()
	{
		StringArrayReader in = new TestReader(new String[0][]);
		new StringArrayReaderIterator(in).next();
	}
	
	@Test
	public void linesFromReaderAreReturned()
	{
		StringArrayReader in = new TestReader(new String[][] {{"1a", "1b"}, {"2a", "2b"}});
		Iterator<String[]> i = new StringArrayReaderIterator(in);
		assertTrue(i.hasNext());
		assertArrayEquals(new String[] {"1a", "1b"}, i.next());
		assertTrue(i.hasNext());
		assertArrayEquals(new String[] {"2a", "2b"}, i.next());
		assertFalse(i.hasNext());
	}

	private static class TestReader implements StringArrayReader
	{
		private final String[][] _lines;
		private int _index;
		
		public TestReader(String[][] lines)
		{
			_lines = lines;
			_index = 0;
		}

		public void close()
		{
		}

		public String[] read() throws IOException
		{
			if (_index < _lines.length) {
				return _lines[_index++];
			}
			throw new EOFException();
		}
		
	}
}
