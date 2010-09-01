package diergo.csv;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import diergo.array.test.MockArrayWriter;

public class FilterTest
{
  private static final Iterable<String[]> IN = Arrays.<String[]> asList(new String[][] { { "1", "bla" },
      { "2", "test" }, { "3", "blub" } });

  @Test
  public void fieldIsFilteredByIndex()
      throws IOException
  {
    Map<String, String> options = new HashMap<String, String>();
    options.put("field", "2");
    options.put("value", "test");
    MockArrayWriter<String> out = new MockArrayWriter<String>();
    new Filter().process(IN, out, options);
    assertEquals(1, out.getResult().size());
    assertArrayEquals(new String[] { "2", "test" }, out.getResult().get(0));
  }

  @Test
  public void headerIsNotFiltered()
      throws IOException
  {
    Map<String, String> options = new HashMap<String, String>();
    options.put("field", "2");
    options.put("value", "test");
    options.put("header", "true");
    MockArrayWriter<String> out = new MockArrayWriter<String>();
    new Filter().process(IN, out, options);
    assertEquals(2, out.getResult().size());
    assertArrayEquals(new String[] { "2", "test" }, out.getResult().get(1));
  }
}
