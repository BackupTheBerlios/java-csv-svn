package diergo.csv;

import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import diergo.array.test.MockArrayWriter;

public class CutTest
{
  private static final Iterable<String[]> IN = Arrays.<String[]> asList(new String[][] { { "1a", "1b", "1c" },
      { "2a", "2b", "2c" } });

  @Test
  public void cutFieldsPassedAsOption()
      throws IOException
  {
    MockArrayWriter<String> out = new MockArrayWriter<String>();
    new Cut().process(IN, out, Collections.singletonMap("fields", "1,3"));
    assertArrayEquals(new String[] { "1a", "1c" }, out.getResult().get(0));
    assertArrayEquals(new String[] { "2a", "2c" }, out.getResult().get(1));
  }

  @Test
  public void defaultCutsFirstField()
      throws IOException
  {
    MockArrayWriter<String> out = new MockArrayWriter<String>();
    new Cut().process(IN, out, Collections.<String, String> emptyMap());
    assertArrayEquals(new String[] { "1a" }, out.getResult().get(0));
    assertArrayEquals(new String[] { "2a" }, out.getResult().get(1));
  }
}
