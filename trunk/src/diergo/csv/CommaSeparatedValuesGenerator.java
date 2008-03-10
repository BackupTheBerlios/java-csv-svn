package diergo.csv;

import java.io.IOException;
import java.io.Writer;
import java.util.regex.Pattern;

/**
 * Generates CSV data. The separator used is configured on construction.
 */
public class CommaSeparatedValuesGenerator
{
	private static final String QUOTE_REPLACEMENT =
		new String(new char[] { CommaSeparatedValues.QUOTE, CommaSeparatedValues.QUOTE });
	private static Pattern QUOTE_PATTERN =
		Pattern.compile(String.valueOf(CommaSeparatedValues.QUOTE));

	private final char _separator;

	public CommaSeparatedValuesGenerator(final char separator)
	{
		_separator = separator;
	}
	
	public void generate(Iterable<String[]> data, Writer out) throws IOException
	{
		for (String[] line : data) {
			boolean first = true;
			for (String elem : line) {
				if (!first) {
					out.append(_separator);
				}
				out.append(quote(elem));
				first = false;
			}
			out.append('\n');
		}
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
