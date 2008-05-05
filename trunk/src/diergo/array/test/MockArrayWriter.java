package diergo.array.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import diergo.array.ArrayWriter;

/**
 * A simple array writer storing all arrays written in a list.
 */
public class MockArrayWriter<E>
        implements ArrayWriter<E>
{
    private final List<E[]> _out;
    private boolean _closed;

    public MockArrayWriter()
    {
        _out = new ArrayList<E[]>();
        _closed = false;
    }

    public void write(E[] data)
        throws IOException
    {
        if (_closed) {
            throw new IOException("Writer is closed");
        }
        _out.add(data);
    }

    public void close()
    {
        _closed = true;
    }

    /**
     * Returns all arrays written to this writer.
     */
    public List<E[]> getResult()
    {
        return _out;
    }
}
