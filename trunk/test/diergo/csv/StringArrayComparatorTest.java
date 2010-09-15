package diergo.csv;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringArrayComparatorTest
{
  @Test
  public void numericOrdering()
  {
    StringArrayComparator comparator = new StringArrayComparator(new int[] { 0 }, new int[] { 0 });
    assertEquals(1, comparator.compare(new String[] { "10" }, new String[] { "5" }));
  }
}
