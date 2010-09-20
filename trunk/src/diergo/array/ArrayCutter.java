package diergo.array;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import diergo.util.transform.Transformer;
import diergo.util.transform.TransformingIterator;

/**
 * A utility class to cut selected fields of arrays.
 */
public class ArrayCutter<E>
    implements Transformer<E[], E[]>
{
  /**
   * Creates a new iterable passing the selected fields only.
   */
  public static <E> Iterable<E[]> cut(Iterable<E[]> in, int[] fields)
  {
    return TransformingIterator.transform(in, new ArrayCutter<E>(fields));
  }

  private final int[] _fields;

  public ArrayCutter(int[] fields)
  {
    _fields = fields;
  }

  /**
   * Returns a new string array with the selected fields only. The order will be
   * as defined by the fields, unknown fields are ignored.
   */
  public E[] transform(E[] values)
  {
    List<E> result = new ArrayList<E>(values.length);
    for (int i : _fields) {
      if (values.length > i) {
        result.add(values[i]);
      }
    }
    return result.toArray(createArrayAs(values, result.size()));
  }

  @SuppressWarnings("unchecked")
  private E[] createArrayAs(E[] values, int size)
  {
    return (E[]) Array.newInstance(values.getClass().getComponentType(), size);
  }
}
