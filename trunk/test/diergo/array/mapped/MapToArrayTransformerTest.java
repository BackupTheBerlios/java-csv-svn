package diergo.array.mapped;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

public class MapToArrayTransformerTest
{
  @Test
  public void emptyIterablesIsStillEmpty()
  {
    Iterable<Integer[]> ints = MapToArrayTransformer.asArrays(new String[] { "h1", "h2" }, Collections
        .<Map<String, Integer>> emptyList());
    assertFalse(ints.iterator().hasNext());
  }

  @Test
  public void headersAreDeternimedFromFirstKeysButNotReturned()
  {
    Map<String, Integer> values = new LinkedHashMap<String, Integer>();
    values.put("h1", 1);
    values.put("h2", 2);
    Iterable<Integer[]> ints = MapToArrayTransformer.asArrays(Collections.singletonList(values));
    assertArrayEquals(new Integer[] { 1, 2 }, ints.iterator().next());
  }

  @Test
  public void valuesAreReturnedWithHeadersAdditionalValuesAreIgnored()
  {
    Map<String, Integer> values = new LinkedHashMap<String, Integer>();
    values.put("h1", 1);
    values.put("h2", 2);
    values.put("h3", 3);
    Iterable<Integer[]> ints = MapToArrayTransformer.asArrays(new String[] { "h1", "h2" }, Collections
        .singletonList(values));
    assertArrayEquals(new Integer[] { 1, 2 }, ints.iterator().next());
  }

  @Test
  public void unknownValuesAreReturnedAsNull()
  {
    Map<String, Integer> values = new LinkedHashMap<String, Integer>();
    values.put("h1", 1);
    Iterable<Integer[]> ints = MapToArrayTransformer.asArrays(new String[] { "h1", "h2" }, Collections
        .singletonList(values));
    assertArrayEquals(new Integer[] { 1, null }, ints.iterator().next());
  }
}
