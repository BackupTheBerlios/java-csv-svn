package diergo.array;

import java.io.Writer;
import java.io.IOException;

public abstract class ArrayLineWriter<E> implements ArrayWriter<E>
{
    private final Writer _out;
    protected final LineParser<E> _parser;
    protected boolean _linesWritten;

    public ArrayLineWriter(Writer out, LineParser<E> parser)
    {
        _out = out;
        _parser = parser;
        _linesWritten = false;
    }

    public void write(E[] values)
        throws IOException
    {
        if (_linesWritten) {
            _out.append('\n');
        }
        try {
            _out.write(_parser.generateLine(values));
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
