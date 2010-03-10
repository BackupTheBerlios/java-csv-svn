package diergo.array;

import java.io.IOException;
import java.io.Writer;

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

  public void write(E... values)
      throws IOException
  {
    if (_linesWritten) {
      _out.append('\n');
    }
    try {
      _out.write(_generator.generateLine(values));
    } catch (IllegalArgumentException e) {
      throw new IOException("Cannot write line");
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
