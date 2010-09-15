package diergo.array.mapped;

import java.util.Map;

import diergo.array.ArrayWriter;

/**
 * This iterator converts maps to string arrays containing a set of specified
 * fields.
 * 
 * This class is useful to pass output from a {@link ValueTransformer} to a
 * {@link ArrayWriter}.
 * 
 * @deprecated use {@link MapToArrayTransformer} instead
 */
@Deprecated
public class UnmappingIterator<E>
    extends MapToArrayTransformer<String, E>
{
  public static Iterable<String[]> iterateAsStringArrays(Iterable<Map<String, String>> source)
  {
    return MapToArrayTransformer.asArrays(source);
  }

  private UnmappingIterator(String[] fields)
  {
    super(fields);
  }
}
