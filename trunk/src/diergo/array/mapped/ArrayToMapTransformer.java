package diergo.array.mapped;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import diergo.util.transform.Transformer;
import diergo.util.transform.TransformingIterator;

/**
 * This transformer converts arrays to maps using an header with field names.
 *
 * @since 2.0
 */
public class ArrayToMapTransformer<K, V>
    implements Transformer<V[], Map<K, V>>
{
  public static <E> Iterable<Map<E, E>> asMaps(Iterable<E[]> source)
  {
    final Iterator<E[]> iterator = source.iterator();
    return new Iterable<Map<E, E>>()
    {
      public Iterator<Map<E, E>> iterator()
      {
        @SuppressWarnings("unchecked")
        E[] header = iterator.hasNext() ? iterator.next() : (E[]) new Object[0];
        return TransformingIterator.transform(iterator, new ArrayToMapTransformer<E, E>(header));
      }
    };
  }

  public static <E> Iterable<Map<String, E>> asMaps(final String[] header, final Iterable<E[]> source)
  {
    return new Iterable<Map<String, E>>()
    {
      public Iterator<Map<String, E>> iterator()
      {
        return TransformingIterator.transform(source.iterator(), new ArrayToMapTransformer<String, E>(header));
      }
    };
  }

  private final K[] header;

  public ArrayToMapTransformer(K[] header)
  {
    this.header = header;
  }

  public Map<K, V> transform(V[] source)
  {
    Map<K, V> result = new LinkedHashMap<K, V>();
    for (int i = 0; i < header.length; ++i) {
      result.put(header[i], i < source.length ? source[i] : null);
    }
    return result;
  }
}
