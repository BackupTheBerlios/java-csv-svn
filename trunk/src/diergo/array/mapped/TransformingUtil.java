package diergo.array.mapped;

import java.util.Iterator;
import java.util.Map;

/**
 * Wraps an iterator on maps to and from an iterator on any type {@code T} using
 * a value transformer.
 * 
 * @since 1.2
 */
public class TransformingUtil
{
  public static <T, E> Iterator<T> wrapMap(final Iterator<Map<String, E>> iterator,
      final ValueTransformer<T, E> transformer)
  {
    return new Iterator<T>()
    {
      public boolean hasNext()
      {
        return iterator.hasNext();
      }

      public T next()
      {
        return transformer.transform(iterator.next());
      }

      public void remove()
      {
        iterator.remove();
      }
    };
  }

  public static <T, E> Iterator<Map<String, E>> wrapValue(final Iterator<T> iterator,
      final ValueTransformer<T, E> transformer)
  {
    return new Iterator<Map<String, E>>()
    {
      public boolean hasNext()
      {
        return iterator.hasNext();
      }

      public Map<String, E> next()
      {
        return transformer.transform(iterator.next());
      }

      public void remove()
      {
        iterator.remove();
      }
    };
  }
}
