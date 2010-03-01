package diergo.array;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterator operating on a reader returning arrays.
 */
public class ArrayReaderIterator<E>
    implements Iterator<E[]>
{
  private final ArrayReader<E> _reader;
  private E[] _nextLine;

  public ArrayReaderIterator(ArrayReader<E> reader)
  {
    _reader = reader;
  }

  /**
   * Tries to read one more line if not read yet.
   * 
   * @return whether a line has been read now or before
   * @see ArrayReader#read()
   */
  public boolean hasNext()
  {
    if (_nextLine == null) {
      try {
        _nextLine = _reader.read();
      } catch (IOException e) {
        throw new IllegalArgumentException("Cannot read next line", e);
      }
    }
    return _nextLine != null;
  }

  /**
   * Returns the next line.
   * 
   * @see #hasNext()
   */
  public E[] next()
  {
    if (!hasNext()) {
      throw new NoSuchElementException();
    }
    try {
      return _nextLine;
    } finally {
      _nextLine = null;
    }
  }

  /**
   * Removal is not supported.
   * 
   * @throws UnsupportedOperationException
   */
  public void remove()
  {
    throw new UnsupportedOperationException("Comma separated value source cannot be modified");
  }

}