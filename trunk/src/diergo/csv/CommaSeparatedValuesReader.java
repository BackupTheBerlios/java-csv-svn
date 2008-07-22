package diergo.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import diergo.array.ArrayReader;
import diergo.array.ArrayReaderIterator;

/**
 * Reads CSV data line based. The separator used and trimming of whitespace for
 * fields are configured on construction.
 */
public class CommaSeparatedValuesReader
        implements ArrayReader<String>, Iterable<String[]>
{
    private final BufferedReader _in;
    private final boolean _trimFields;
    private char _separator;
    private SeparatorDeterminer _determiner;

    /**
     * Creates a reader for CSV data using the underlying reader.
     * 
     * @param in
     *            the underlying reader
     * @param separator
     *            the separator of the fields in a line
     * @param trimFields
     *            whether the field values will be trimmed
     */
    public CommaSeparatedValuesReader(Reader in, char separator, boolean trimFields)
    {
        _in = in instanceof BufferedReader ? (BufferedReader) in : new BufferedReader(in);
        _separator = separator;
        _trimFields = trimFields;
    }


    public CommaSeparatedValuesReader(Reader in, SeparatorDeterminer separatorDeterminer, boolean trimFields)
    {
        _in = in instanceof BufferedReader ? (BufferedReader) in : new BufferedReader(in);
        _determiner = separatorDeterminer;
        _trimFields = trimFields;
    }

    /**
     * Reads the next line and parse it as CSV data.
     * 
     * @see BufferedReader#readLine()
     */
    public String[] read()
        throws IOException
    {
        String line = _in.readLine();
        if (_determiner != null) {
            _separator = _determiner.determineSeparator(line);
            _determiner = null;
        }
        return line == null ? null : parseLine(line);
    }

    /**
     * Closes the underlying reader.
     */
    public void close()
        throws IOException
    {
        _in.close();
    }

    public Iterator<String[]> iterator()
    {
        return new ArrayReaderIterator<String>(this);
    }

    private String[] parseLine(String line)
        throws IOException
    {
        return CommaSeparatedValuesParser.parseLine(line, _separator, _trimFields);
    }

}
