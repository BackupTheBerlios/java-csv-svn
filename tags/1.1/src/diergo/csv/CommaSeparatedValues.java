package diergo.csv;

import java.io.*;

/**
 * Convenience access to {@link CommaSeparatedValuesReader} and
 * {@link CommaSeparatedValuesWriter} to be used in {@code for()} constructs.
 */
public class CommaSeparatedValues
{
    /**
     * Parses the data using one of the default separators.
     * @see AutoSeparatorDeterminer#DEFAULT_SEPARATORS
     */
    public static Iterable<String[]> parse(Reader csvData)
    {
        return new CommaSeparatedValuesReader(csvData, new AutoSeparatorDeterminer(AutoSeparatorDeterminer.DEFAULT_SEPARATORS), true);
    }

    /**
     * Parses the data using the passed separator.
     */
    public static Iterable<String[]> parse(Reader csvData, char separator)
    {
        return new CommaSeparatedValuesReader(csvData, new FixedSeparatorDeterminer(separator), true);
    }

    /**
     * Generates data using the passed separator.
     * @see #generate(Iterable, char, Writer)
     */
    public static String generate(Iterable<String[]> data, char separator)
    {
        StringWriter out = new StringWriter();
        try {
            generate(data, separator, out);
        } catch (IOException e) {
            throw new AssertionError(e.getMessage());
        }
        return out.toString();
    }

    /**
     * Generates data to the writer using the passed separator.
     */
    public static void generate(Iterable<String[]> data, char separator, Writer out)
        throws IOException
    {
        CommaSeparatedValuesWriter generator = new CommaSeparatedValuesWriter(out, separator);
        for (String[] line : data) {
            generator.write(line);
        }
    }
}
