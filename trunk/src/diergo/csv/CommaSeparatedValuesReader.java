package diergo.csv;

import java.io.Reader;
import java.util.Iterator;

import diergo.array.ArrayLineReader;
import diergo.array.ArrayReaderIterator;

/**
 * Reads CSV data line based. The separator used and trimming of whitespace for
 * fields are configured on construction.
 */
public class CommaSeparatedValuesReader
        extends ArrayLineReader<String>
        implements Iterable<String[]>
{
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
        super(in, new CommaSeparatedValuesParser(separator, trimFields));
    }


    public CommaSeparatedValuesReader(Reader in, SeparatorDeterminer separatorDeterminer, boolean trimFields)
    {
        super(in, new CommaSeparatedValuesParser(separatorDeterminer, trimFields));
    }

    public Iterator<String[]> iterator()
    {
        return new ArrayReaderIterator<String>(this);
    }

    public CommaSeparatedValuesParser getParser()
    {
        return (CommaSeparatedValuesParser)_parser;
    }
}
