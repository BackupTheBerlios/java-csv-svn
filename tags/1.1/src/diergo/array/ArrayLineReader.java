package diergo.array;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.IOException;

public abstract class ArrayLineReader<E> implements ArrayReader<E>
{
    private final BufferedReader _in;
    protected final LineParser<E> _parser;

    public ArrayLineReader(Reader in, LineParser<E> parser)
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

    /**
     * Closes the underlying reader.
     */
    public void close()
        throws IOException
    {
        _in.close();
    }
}
