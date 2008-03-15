package diergo.csv;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Convenience access to {@link CommaSeparatedValuesReader} and
 * {@link CommaSeparatedValuesWriter} to be used in {@code for()}
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
				return new CsvInterator(new CommaSeparatedValuesReader(csvData, excelMode ? ';' : ',', excelMode));
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
		CommaSeparatedValuesWriter generator = new CommaSeparatedValuesWriter(out, excelMode ? ';' : ',');
		for (String[] line : data) {
			generator.write(line);
		}
	}
	
	private static class CsvInterator
		implements Iterator<String[]>
	{
		private final CommaSeparatedValuesReader _parser;
		private String[] _nextLine;
	
		public CsvInterator(CommaSeparatedValuesReader parser)
		{
			_parser = parser;
		}
	
		public boolean hasNext()
		{
			if (_nextLine == null) {
				try {
					_nextLine = _parser.read();
				} catch (IOException e) {
					return false;
				}
			}
			return _nextLine != null;
		}
	
		public String[] next()
		{
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			try {
				return _nextLine;
			} finally {
				_nextLine = null;
			}
		}
	
		public void remove()
		{
			throw new UnsupportedOperationException("Comma separated value source cannot be modified");
		}
		
	}
}
