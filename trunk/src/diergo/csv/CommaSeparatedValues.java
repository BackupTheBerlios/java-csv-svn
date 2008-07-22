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

    public static Iterable<String[]> parse(Reader csvData)
    {
        return parse(csvData, SeparatorDeterminer.DEFAULT_SEPARATORS);
    }

    public static Iterable<String[]> parse(Reader csvData, String possibleSeparators)
    {
        return new CommaSeparatedValuesReader(csvData, new SeparatorDeterminer(possibleSeparators), true);
    }

    @Deprecated
    public static Iterable<String[]> parse(Reader csvData, boolean excelMode)
    {
        return new CommaSeparatedValuesReader(csvData, excelMode ? ';' : ',', excelMode);
    }

    public static Iterable<String[]> parse(String csvData)
    {
        return parse(new StringReader(csvData));
    }

    public static Iterable<String[]> parse(String csvData, String possibleSeparators)
    {
        return parse(new StringReader(csvData), possibleSeparators);
    }

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
        CommaSeparatedValuesWriter generator = new CommaSeparatedValuesWriter(out, excelMode ? ';' : ',');
        for (String[] line : data) {
            generator.write(line);
        }
    }
}
