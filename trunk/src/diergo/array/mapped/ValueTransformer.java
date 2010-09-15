package diergo.array.mapped;

import java.util.Map;

/**
 * A value transformer creates an object of type T from a value map and vice
 * versa.
 * 
 * @deprecated use {@link Transformer} instead
 */
@Deprecated
public interface ValueTransformer<T, E>
{
  public T transform(Map<String, E> data);

  public Map<String, E> transform(T value);
}
