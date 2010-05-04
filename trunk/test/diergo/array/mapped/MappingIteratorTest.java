package diergo.array.mapped;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.Test;

public class MappingIteratorTest
{
  @Test(expected = NoSuchElementException.class)
  public void emptyIteratorWithoutHeaderDoesNotWotk()
  {
    MappingIterator.iterateAsMaps(Arrays.<String[]> asList()).iterator();
  }

  @Test
  public void emptyIteratorIsStillEmpty()
  {
    Iterator<Map<String, Integer>> iterator = new MappingIterator<Integer>(new String[0], Arrays.<Integer[]> asList()
        .iterator());
    assertFalse(iterator.hasNext());
  }

  @Test(expected = NoSuchElementException.class)
  public void emptyIteratorNextRaisesError()
  {
    new MappingIterator<Integer>(new String[0], Arrays.<Integer[]> asList().iterator()).next();
  }

  @Test
  public void headerOnlyIteratorIsStillEmpty()
  {
    Iterator<Map<String, String>> iterator = MappingIterator.iterateAsMaps(
        Arrays.asList(new String[][] { { "h1", "h2" } })).iterator();
    assertFalse(iterator.hasNext());
  }

  @Test(expected = NoSuchElementException.class)
  public void headerOnlyIteratorHandlesNext()
  {
    MappingIterator.iterateAsMaps(Arrays.asList(new String[][] { { "h1", "h2" } })).iterator().next();
  }

  @Test
  public void valuesAreMapped()
  {
    Map<String, Integer> result = new MappingIterator<Integer>(new String[] { "h1", "h2" }, Arrays.asList(
        new Integer[][] { { 1, 2 } }).iterator()).next();
    assertEquals(1, result.get("h1").intValue());
    assertEquals(2, result.get("h2").intValue());
  }

  @Test
  public void valuesAreMappedAsNull()
  {
    Map<String, Integer> result = new MappingIterator<Integer>(new String[] { "h1", "h2" }, Arrays.asList(
        new Integer[][] { { 1 } }).iterator()).next();
    assertEquals(1, result.get("h1").intValue());
    assertNull(result.get("h2"));
  }
}
