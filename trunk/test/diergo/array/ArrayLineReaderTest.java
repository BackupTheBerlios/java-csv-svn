package diergo.array;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import diergo.util.transform.Transformer;

public class ArrayLineReaderTest
{
  @Test
  public void emptyReaderResultsInNullRead()
      throws IOException
  {
    assertNull(createReader("").read());
  }

  @Test
  public void emptyReaderResultsInIteratorWithoutNext()
      throws IOException
  {
    assertFalse(createIterable("").iterator().hasNext());
  }

  @Test
  public void readerReturnsLineByLine()
      throws IOException
  {
    ArrayLineReader<String> reader = createReader("first\nsecond");
    Assert.assertArrayEquals(new String[] { "first" }, reader.read());
    Assert.assertArrayEquals(new String[] { "second" }, reader.read());
    assertFalse(reader.iterator().hasNext());
  }

  @Test
  public void readerReturnsIterableLines()
      throws IOException
  {
    Iterator<String[]> reader = createIterable("first\nsecond").iterator();
    Assert.assertArrayEquals(new String[] { "first" }, reader.next());
    Assert.assertArrayEquals(new String[] { "second" }, reader.next());
    assertFalse(reader.hasNext());
  }


  private ArrayLineReader<String> createReader(String input)
  {
    return new ArrayLineReader<String>(new StringReader(input), new TestTransformer());
  }

  private Iterable<String[]> createIterable(String input)
  {
    return ArrayLineReader.read(new StringReader(input), new TestTransformer());
  }

  private class TestTransformer
      implements Transformer<String, String[]>
  {
    public String[] transform(String s)
    {
      return new String[] { s };
    }
  }
}
