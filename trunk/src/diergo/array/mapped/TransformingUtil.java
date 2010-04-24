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
  public static <T, E> Iterable<T> iterateAs(Class<T> type, final Iterable<Map<String, E>> iterable,
      final ValueTransformer<? extends T, E> transformer)
  {
    return new Iterable<T>()
    {
      public Iterator<T> iterator()
      {
        return new DelegatingIterator<Map<String, E>, T>(iterable.iterator()) {

          public T next()
          {
            return transformer.transform(_iterator.next());
          }
          
        };
      }
    };
  }

  public static <T, E> Iterator<Map<String, E>> iterateMaps(final Iterator<T> iterable,
      final ValueTransformer<T, E> transformer)
  {
    return new DelegatingIterator<T,Map<String, E>>(iterable) {

      public Map<String, E> next()
      {
        return transformer.transform(_iterator.next());
      }
      
    };
  }
  
  private abstract static class DelegatingIterator<S,T> implements Iterator<T> {

    protected final Iterator<? extends S> _iterator;

    public DelegatingIterator(Iterator<? extends S> iterator)
    {
      _iterator = iterator;
    }

    public boolean hasNext()
    {
      return _iterator.hasNext();
    }

    public void remove()
    {
      _iterator.remove();
    }
    
  }
}
