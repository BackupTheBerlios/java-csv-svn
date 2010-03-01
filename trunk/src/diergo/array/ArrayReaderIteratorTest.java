package diergo.array;

import diergo.array.test.MockArrayReader;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayReaderIteratorTest
{
  @Test
  public void emptyReaderResultsInEmptyIterator()
  {
    ArrayReader<String> in = new MockArrayReader<String>(Collections.<String[]>emptyList());
    assertFalse(new ArrayReaderIterator<String>(in).hasNext());
  }

  @Test(expected = NoSuchElementException.class)
  public void emptyReaderThrowsExceptionOnNext()
  {
    ArrayReader<String> in = new MockArrayReader<String>(Collections.<String[]>emptyList());
    new ArrayReaderIterator<String>(in).next();
  }

  @Test
  public void linesFromReaderAreReturned()
  {
    ArrayReader<String> in = new MockArrayReader<String>(Arrays
        .asList(new String[][] { { "1a", "1b" }, { "2a", "2b" } }));
    Iterator<String[]> i = new ArrayReaderIterator<String>(in);
    assertTrue(i.hasNext());
    assertArrayEquals(new String[] { "1a", "1b" }, i.next());
    assertTrue(i.hasNext());
    assertArrayEquals(new String[] { "2a", "2b" }, i.next());
    assertFalse(i.hasNext());
  }
}
