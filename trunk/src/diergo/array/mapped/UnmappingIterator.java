package diergo.array.mapped;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import diergo.array.ArrayWriter;

/**
 * This iterator converts maps to string arrays containing a set of specified
 * fields.
 * 
 * This class is useful to pass output from a {@link ValueTransformer} to a
 * {@link ArrayWriter}.
 */
public class UnmappingIterator<E>
    implements Iterator<String[]>
{
  public static <E> Iterable<String[]> iterateAsStringArrays(Iterable<Map<String, E>> source)
  {
    final Iterator<Map<String, E>> iterator = source.iterator();
    return new Iterable<String[]>()
    {

      public Iterator<String[]> iterator()
      {
        return new UnmappingIterator<E>(iterator);
      }
    };
  }

  private final Iterator<Map<String, E>> _iterator;
  private String[] _fields;
  private Map<String, E> _nextValues;

  public UnmappingIterator(String[] fields, Iterator<Map<String, E>> iterator)
  {
    _fields = fields;
    _iterator = iterator;
    _nextValues = null;
  }

  public UnmappingIterator(Iterator<Map<String, E>> iterator)
  {
    this(null, iterator);
  }

  public boolean hasNext()
  {
    return _nextValues != null || _iterator.hasNext();
  }

  public String[] next()
  {
    if (_fields == null) {
      _nextValues = _iterator.next();
      _fields = new ArrayList<String>(_nextValues.keySet()).toArray(new String[_nextValues.size()]);
      return _fields;
    } else {
      Map<String, E> values = _nextValues == null ? _iterator.next() : _nextValues;
      _nextValues = null;
      String[] result = new String[_fields.length];
      int i = 0;
      for (String key : _fields) {
        E value = values.get(key);
        result[i++] = value == null ? null : String.valueOf(value);
      }
      return result;
    }
  }

  public void remove()
  {
    _iterator.remove();
  }

}
