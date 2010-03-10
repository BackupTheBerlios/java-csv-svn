package diergo.array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

public class ArrayLineReader<E>
    implements ArrayReader<E>, Iterable<E[]>
{
  private final BufferedReader _in;
  protected final ArrayLineParser<E> _parser;

  public ArrayLineReader(Reader in, ArrayLineParser<E> parser)
  {
    _in = in instanceof BufferedReader ? (BufferedReader) in : new BufferedReader(in);
    _parser = parser;
  }

  public E[] read()
      throws IOException
  {
    String line = _in.readLine();
    try {
      return line == null ? null : _parser.parseLine(line);
    } catch (IllegalArgumentException e) {
      throw new IOException("Cannot read line: " + e.getMessage());
    }
  }

  public Iterator<E[]> iterator()
  {
    return new ArrayReaderIterator<E>(this);
  }

  /**
   * Closes the underlying reader.
   */
  public void close()
      throws IOException
  {
    _in.close();
  }
}
