package diergo.csv;

import java.io.IOException;
import java.io.Writer;

import diergo.array.ArrayWriter;

/**
 * Writes CSV data from String arrays to an underlying writer. The separator
 * used is configured on construction.
 */
public class CommaSeparatedValuesWriter
        implements ArrayWriter<String>
{

    private final Writer _out;
    private final char _separator;
    private boolean _linesWritten;

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
        _out = out;
        _separator = separator;
        _linesWritten = false;
    }

    /**
     * Writes a line of CSV data.
     */
    public void write(String[] line)
        throws IOException
    {
        if (_linesWritten) {
            _out.append('\n');
        }
        _out.append(CommaSeparatedValuesParser.write(line, _separator));
        _linesWritten = true;
    }

    public void close()
        throws IOException
    {
        _out.close();
    }

}
