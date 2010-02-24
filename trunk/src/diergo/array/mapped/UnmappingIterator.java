package diergo.array.mapped;

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
  private final Iterator<Map<String, E>> _iterator;
  private final String[] _fields;
  private boolean _header;

  public UnmappingIterator(String[] fields, Iterator<Map<String, E>> iterator)
  {
    _iterator = iterator;
    _fields = fields;
    _header = true;
  }

  public boolean hasNext()
  {
    return _header ? true : _iterator.hasNext();
  }

  public String[] next()
  {
    if (_header) {
      _header = false;
      return _fields;
    } else {
      Map<String, E> values = _iterator.next();
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
