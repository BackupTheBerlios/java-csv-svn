package diergo.array;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;
import java.util.Arrays;

import org.junit.Test;

import diergo.util.transform.Transformer;

public class ArrayLineWriterTest
{
  @Test
  public void noElementsBecomeEmptyOutput()
  {
    assertEquals("", write());
  }

  @Test
  public void lineIsWritten()
  {
    assertEquals("[a, b]", write(new String[] { "a", "b" }));
  }

  @Test
  public void linesAreWrittenLineByLine()
  {
    assertEquals("[a1, b1]\n[a2, b2]", write(new String[] { "a1", "b1" }, new String[] { "a2", "b2" }));
  }

  private String write(String[]... source)
  {
    return ArrayLineWriter.write(Arrays.asList(source), new TestTransformer(), new StringWriter()).toString();
  }

  private class TestTransformer
      implements Transformer<String[], String>
  {
    public String transform(String[] strings)
    {
      return Arrays.toString(strings);
    }
  }
}
