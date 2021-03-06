package diergo.csv;

import java.io.Writer;

import diergo.array.ArrayLineWriter;

/**
 * Writes CSV data from String arrays to an underlying writer. The separator
 * used is configured on construction.
 */
public class CommaSeparatedValuesWriter
        extends ArrayLineWriter<String>
{

    /**
     * Creates a writer for CSV data using the underlying writer.
     * 
     * @param out
     *            the underlying writer
     * @param separator
     *            the separator of the fields in a line
     */
    public CommaSeparatedValuesWriter(Writer out, char separator)
    {
        this(out, new CommaSeparatedValuesParser(new FixedSeparatorDeterminer(separator), true));
    }

    public CommaSeparatedValuesWriter(Writer out, CommaSeparatedValuesParser parser)
    {
        super(out, parser);
    }

}
