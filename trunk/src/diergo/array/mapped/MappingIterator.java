package diergo.array.mapped;

import java.util.Iterator;
import java.util.Map;

/**
 * This iterator converts arrays to maps using the first string array as an
 * header with field names.
 * 
 * This class is useful to create input for a {@link ValueTransformer} from a
 * {@link Iterator}.
 * 
 * @since 1.2
 * @deprecated use {@link ArrayToMapTransformer} instead
 */
@Deprecated
public class MappingIterator<E>
    extends ArrayToMapTransformer<String, E>
{
  public static Iterable<Map<String, String>> iterateAsMaps(Iterable<String[]> source)
  {
    return ArrayToMapTransformer.asMaps(source);
  }

  private MappingIterator(String[] header)
  {
    super(header);
  }
}
