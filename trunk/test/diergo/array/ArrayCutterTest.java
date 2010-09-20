package diergo.array;

import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;

import org.junit.Test;

public class ArrayCutterTest
{
  @Test
  public void onlySelectedValuesAreReturned()
      throws IOException
  {
    assertArrayEquals(new String[] { "1", "3" }, new ArrayCutter<String>(new int[] { 1, 3 }).transform(new String[] {
        "0", "1", "2", "3" }));
  }

  @Test
  public void fieldsTooLargeAreIgnored()
      throws IOException
  {
    assertArrayEquals(new String[] { "1" }, new ArrayCutter<String>(new int[] { 1, 3 }).transform(new String[] { "0",
        "1" }));
  }

  @Test
  public void fieldsAreRearranged()
      throws IOException
  {
    assertArrayEquals(new String[] { "2", "1", "0" }, new ArrayCutter<String>(new int[] { 2, 1, 0 })
        .transform(new String[] { "0", "1", "2" }));
  }
}
