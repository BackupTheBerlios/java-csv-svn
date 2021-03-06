package diergo.csv;

import java.io.IOException;
import java.io.Writer;
import java.util.regex.Pattern;

import diergo.array.ArrayWriter;

/**
 * Writes CSV data from String arrays to an underlying writer. The separator
 * used is configured on construction.
 */
public class CommaSeparatedValuesWriter
        implements ArrayWriter<String>
{
    private static final String QUOTE_REPLACEMENT = new String(new char[] { CommaSeparatedValues.QUOTE,
            CommaSeparatedValues.QUOTE });
    private static final Pattern QUOTE_PATTERN = Pattern.compile(String.valueOf(CommaSeparatedValues.QUOTE));

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
        boolean first = true;
        for (String elem : line) {
            if (!first) {
                _out.append(_separator);
            }
            _out.append(quote(elem));
            first = false;
        }
        _linesWritten = true;
    }

    public void close()
        throws IOException
    {
        _out.close();
    }

    private String quote(String elem)
    {
        boolean containsQuote = elem.indexOf(CommaSeparatedValues.QUOTE) != -1;
        boolean quote = elem.indexOf(_separator) != -1 || containsQuote;
        if (quote) {
            if (containsQuote) {
                elem = QUOTE_PATTERN.matcher(elem).replaceAll(QUOTE_REPLACEMENT);
            }
            elem = CommaSeparatedValues.QUOTE + elem + CommaSeparatedValues.QUOTE;
        }
        return elem;
    }
}
