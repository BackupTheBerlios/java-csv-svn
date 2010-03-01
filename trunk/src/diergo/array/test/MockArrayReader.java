package diergo.array.test;

import diergo.array.ArrayReader;

import java.util.Collections;
import java.util.List;

/**
 * A simple array reader reading all arrays from a list.
 */
public class MockArrayReader<E>
    implements ArrayReader<E>
{
  private List<E[]> _in;
  private int _i;

  public MockArrayReader(List<E[]> in)
  {
    setArrays(in);
  }

  public MockArrayReader()
  {
    this(Collections.<E[]> emptyList());
  }

  public E[] read()
  {
    if (_i < _in.size()) {
      return _in.get(_i++);
    }
    return null;
  }

  public void close()
  {
    _i = _in.size();
  }

  /**
   * Sets the arrays to be read.
   */
  public void setArrays(List<E[]> in)
  {
    _in = in;
    _i = 0;
  }

}
