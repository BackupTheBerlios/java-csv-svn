package diergo.array.mapped;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import diergo.util.transform.Transformer;
import diergo.util.transform.TransformingIterator;

/**
 * This iterator converts maps to string arrays containing a set of specified
 * fields.
 */
public class MapToArrayTransformer<K, V>
    implements Transformer<Map<K, V>, V[]>
{
  public static <E> Iterable<E[]> asArrays(final String[] header, final Iterable<Map<String, E>> source)
  {
    return TransformingIterator.transform(source, new MapToArrayTransformer<String, E>(header));
  }

  public static <E> Iterable<E[]> asArrays(final Iterable<Map<String, E>> source)
  {
    return new Iterable<E[]>()
    {
      private final Iterator<Map<String, E>> delegate = source.iterator();
      private Map<String, E> first;
      private MapToArrayTransformer<String, E> transformer;

      public Iterator<E[]> iterator()
      {
        return new Iterator<E[]>()
        {
          public void remove()
          {
            if (first != null) {
              throw new UnsupportedOperationException("The header cannot be removed");
            }
            delegate.remove();
          }

          public E[] next()
          {
            if (transformer == null) {
              first = delegate.next();
              String[] header = new ArrayList<String>(first.keySet()).toArray(new String[first.size()]);
              transformer = new MapToArrayTransformer<String, E>(header);
            }
            try {
              return transformer.transform(first == null ? delegate.next() : first);
            } finally {
              first = null;
            }
          }

          public boolean hasNext()
          {
            return first != null || delegate.hasNext();
          }
        };
      }
    };
  }

  private final K[] _fields;

  public MapToArrayTransformer(K[] fields)
  {
    _fields = fields;
  }

  public V[] transform(Map<K, V> source)
  {
    @SuppressWarnings("unchecked")
    V[] result = (V[]) new Object[_fields.length];
    int i = 0;
    for (K key : _fields) {
      V value = source.get(key);
      result[i++] = value;
    }
    return result;
  }
}
