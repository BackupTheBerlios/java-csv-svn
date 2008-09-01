package diergo.csv;

import java.io.*;

/**
 * Convenience access to {@link CommaSeparatedValuesReader} and
 * {@link CommaSeparatedValuesWriter} to be used in {@code for()} constructs.
 * The flag {@code excelMode} switches the separator ({@code ;} on
 * {@code excelMode} otherwise {@code ,}) and the white space trimming of field
 * values (used on {@code excelMode}).
 */
public class CommaSeparatedValues
{
    /**
     * Parses the data using one of the default separators.
     * @see #parse(java.io.Reader, String)
     * @see AutoSeparatorDeterminer#DEFAULT_SEPARATORS
     */
    public static Iterable<String[]> parse(Reader csvData)
    {
        return parse(csvData, AutoSeparatorDeterminer.DEFAULT_SEPARATORS);
    }

    /**
     * Parses the data using the determined separator.
     * @see AutoSeparatorDeterminer
     */
    public static Iterable<String[]> parse(Reader csvData, String possibleSeparators)
    {
        return new CommaSeparatedValuesReader(csvData, new AutoSeparatorDeterminer(possibleSeparators), true);
    }

    /**
     * Parses the data using comma or semicolon (@code{excelMode}) as separator.
     * @deprecated Use {@link #parse(java.io.Reader)} instead
     */
    @Deprecated
    public static Iterable<String[]> parse(Reader csvData, boolean excelMode)
    {
        return new CommaSeparatedValuesReader(csvData, excelMode ? ';' : ',', excelMode);
    }

    /**
     * @deprecated Use {@link #parse(java.io.Reader)} with a {@link java.io.StringReader} instead
     */
    @Deprecated
    public static Iterable<String[]> parse(String csvData)
    {
        return parse(new StringReader(csvData));
    }

    /**
     * @see #parse(java.io.Reader, boolean) 
     * @deprecated Use {@link #parse(java.io.Reader)} with a {@link java.io.StringReader} instead
     */
    @Deprecated
    public static Iterable<String[]> parse(String csvData, boolean excelMode)
    {
        return parse(new StringReader(csvData), excelMode);
    }

    public static String generate(Iterable<String[]> data, boolean excelMode)
    {
        StringWriter out = new StringWriter();
        try {
            generate(data, excelMode, out);
        } catch (IOException e) {

        }
        return out.toString();
    }

    public static void generate(Iterable<String[]> data, boolean excelMode, Writer out)
        throws IOException
    {
        generate(data, excelMode ? ';' : ',', out);
    }

    public static void generate(Iterable<String[]> data, char separator, Writer out)
        throws IOException
    {
        CommaSeparatedValuesWriter generator = new CommaSeparatedValuesWriter(out, separator);
        for (String[] line : data) {
            generator.write(line);
        }
    }
}
