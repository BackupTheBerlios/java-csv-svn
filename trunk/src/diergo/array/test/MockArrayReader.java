package diergo.array.test;

import java.io.IOException;
import java.util.List;

import diergo.array.ArrayReader;

public class MockArrayReader<E>
        implements ArrayReader<E>
{
    private final List<E[]> _in;
    private int _i;

    public MockArrayReader(List<E[]> in)
    {
        _in = in;
        _i = 0;
    }

    public E[] read()
        throws IOException
    {
        if (_i < _in.size()) {
            return _in.get(_i++);
        }
        throw new IOException("No more data, reader closed or EOF");
    }

    public void close()
    {
        _i = _in.size();
    }

}
