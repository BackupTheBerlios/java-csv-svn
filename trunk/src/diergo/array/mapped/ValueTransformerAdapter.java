package diergo.array.mapped;

import java.util.Map;

import diergo.util.transform.Transformer;

/**
 * An adapter to use the former value transformer as normal transformers.
 *
 * @since 2.0
 * @deprecated use {@link Transformer} instead
 */
@Deprecated
public class ValueTransformerAdapter
{
  public static <T, E> Transformer<T, Map<String, E>> toMap(final ValueTransformer<T, E> delegate)
  {
    return new Transformer<T, Map<String, E>>()
    {
      public Map<String, E> transform(T source)
      {
        return delegate.transform(source);
      }
    };
  }

  public static <T, E> Transformer<Map<String, E>, T> fromMap(final ValueTransformer<T, E> delegate)
  {
    return new Transformer<Map<String, E>, T>()
    {
      public T transform(Map<String, E> source)
      {
        return delegate.transform(source);
      }
    };
  }
}
