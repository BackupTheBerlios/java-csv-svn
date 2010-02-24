package diergo.array;

import java.io.Writer;
import java.io.IOException;

public class ArrayLineWriter<E>
    implements ArrayWriter<E>
{
  private final Writer _out;
  protected final ArrayLineGenerator<E> _generator;
  protected boolean _linesWritten;

  public ArrayLineWriter(Writer out, ArrayLineGenerator<E> generator)
  {
    _out = out;
    _generator = generator;
    _linesWritten = false;
  }

  public void write(E[] values)
      throws IOException
  {
    if (_linesWritten) {
      _out.append('\n');
    }
    try {
      _out.write(_generator.generateLine(values));
    } catch (IllegalArgumentException e) {
      throw new IOException("Cannot write line: " + e.getMessage());
    } finally {
      _linesWritten = true;
    }
  }

  public void close()
      throws IOException
  {
    _out.close();
  }
}
