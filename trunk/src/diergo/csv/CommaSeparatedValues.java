package diergo.csv;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;

/**
 * Convenience access to {@link CommaSeparatedValuesParser} and
 * {@link CommaSeparatedValuesGenerator} to be used in {@code for()}
 * constructs.
 * The flag {@code excelMode} switches the separator ({@code ;} on
 * {@code excelMode} otherwise {@code ,}) and the white space trimming
 * of field values (used on {@code excelMode}).
 */
public class CommaSeparatedValues
{
	public static char QUOTE = '"';

	public static Iterable<String[]> parse(final Reader csvData, final boolean excelMode)
	{
		return new Iterable<String[]>()
		{
			public Iterator<String[]> iterator() {
				return new CommaSeparatedValuesParser(excelMode ? ';' : ',', excelMode).parse(csvData);
			}
		};
	}

	public static Iterable<String[]> parse(final String csvData, final boolean excelMode)
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
		new CommaSeparatedValuesGenerator(excelMode ? ';' : ',').generate(data, out);
	}
	
}
