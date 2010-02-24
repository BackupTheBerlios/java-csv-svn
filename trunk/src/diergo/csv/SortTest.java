package diergo.csv;

import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import diergo.array.test.MockArrayWriter;

public class SortTest
{
  @Test
  public void linesAreSortedByOrderOption()
      throws IOException
  {
    Iterable<String[]> in = Arrays.asList(new String[][] { { "2a", "2b", "2c" }, { "1a", "2b", "3c" },
        { "3a", "1b", "2c" } });
    MockArrayWriter<String> out = new MockArrayWriter<String>();
    new Sort().process(in, out, Collections.singletonMap("order", "2,3"));
    assertArrayEquals(new String[] { "3a", "1b", "2c" }, out.getResult().get(0));
    assertArrayEquals(new String[] { "2a", "2b", "2c" }, out.getResult().get(1));
    assertArrayEquals(new String[] { "1a", "2b", "3c" }, out.getResult().get(2));
  }

  @Test
  public void defaultUsesFirstFieldAsOrderWithoutHeader()
      throws IOException
  {
    Iterable<String[]> in = Arrays.asList(new String[][] { { "2a", "2b" }, { "1a", "1b" } });
    MockArrayWriter<String> out = new MockArrayWriter<String>();
    new Sort().process(in, out, Collections.<String, String> emptyMap());
    assertArrayEquals(new String[] { "1a", "1b" }, out.getResult().get(0));
    assertArrayEquals(new String[] { "2a", "2b" }, out.getResult().get(1));
  }

  @Test
  public void numericOrderIsUsedOnOptionFieldsEndingWithN()
      throws IOException
  {
    Iterable<String[]> in = Arrays.asList(new String[][] { { "1", "10" }, { "2", "2" } });
    MockArrayWriter<String> out = new MockArrayWriter<String>();
    new Sort().process(in, out, Collections.singletonMap("order", "2n"));
    assertArrayEquals(new String[] { "2", "2" }, out.getResult().get(0));
    assertArrayEquals(new String[] { "1", "10" }, out.getResult().get(1));
  }

  @Test
  public void headerIsIgnoredIfOptionSet()
      throws IOException
  {
    Iterable<String[]> in = Arrays.asList(new String[][] { { "2a", "2b" }, { "1a", "1b" } });
    MockArrayWriter<String> out = new MockArrayWriter<String>();
    new Sort().process(in, out, Collections.singletonMap("header", "true"));
    assertArrayEquals(new String[] { "2a", "2b" }, out.getResult().get(0));
    assertArrayEquals(new String[] { "1a", "1b" }, out.getResult().get(1));
  }
}
