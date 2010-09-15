package diergo.array.mapped;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.Test;

public class ArrayToMapTransformerTest
{
  @Test
  public void headerOnlyIteratorIsStillEmpty()
  {
    Iterator<Map<String, String>> iterator = ArrayToMapTransformer.asMaps(
        Arrays.asList(new String[][] { { "h1", "h2" } })).iterator();
    assertFalse(iterator.hasNext());
  }

  @Test(expected = NoSuchElementException.class)
  public void headerOnlyIteratorHandlesNext()
  {
    ArrayToMapTransformer.asMaps(Arrays.asList(new String[][] { { "h1", "h2" } })).iterator().next();
  }

  @Test
  public void valuesAreMapped()
  {
    Map<String, Integer> result = new ArrayToMapTransformer<String, Integer>(new String[] { "h1", "h2" })
        .transform(new Integer[] { 1, 2 });
    assertEquals(1, result.get("h1").intValue());
    assertEquals(2, result.get("h2").intValue());
  }

  @Test
  public void valuesAreMappedAsNull()
  {
    Map<String, Integer> result = new ArrayToMapTransformer<String, Integer>(new String[] { "h1", "h2" })
        .transform(new Integer[] { 1 });
    assertEquals(1, result.get("h1").intValue());
    assertNull(result.get("h2"));
  }

  @Test
  public void noHeadersResultsInEmptyMap()
  {
    Map<String, Integer> result = new ArrayToMapTransformer<String, Integer>(new String[0])
        .transform(new Integer[] { 1 });
    assertEquals(0, result.size());
  }
}
