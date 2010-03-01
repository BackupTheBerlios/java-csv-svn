package diergo.array.mapped;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This iterator converts arrays to maps using the first string array as an
 * header with field names.
 * 
 * This class is useful to create input for a {@link ValueTransformer} from a
 * {@link Iterator}.
 * 
 * @since 1.2
 */
public class MappingIterator<E>
    implements Iterator<Map<String, E>>
{
  public static Iterable<Map<String, String>> createWithHeaders(Iterable<String[]> source)
  {
    final Iterator<String[]> iterator = source.iterator();
    return new Iterable<Map<String, String>>() {

      public Iterator<Map<String, String>> iterator() {
        return new MappingIterator<String>(iterator.next(), iterator);
      }
    };
  }

  private final String[] _header;
  private final Iterator<E[]> _iterator;

  public MappingIterator(String[] header, Iterator<E[]> iterator)
  {
    _header = header;
    _iterator = iterator;
  }

  public boolean hasNext()
  {
    return _iterator.hasNext();
  }

  public Map<String, E> next()
  {
    E[] next = _iterator.next();
    Map<String, E> result = new LinkedHashMap<String, E>();
    for (int i = 0; i < _header.length; ++i) {
      result.put(_header[i], i < next.length ? next[i] : null);
    }
    return result;
  }

  public void remove()
  {
    _iterator.remove();
  }

}
