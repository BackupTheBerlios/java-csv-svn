package diergo.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads CSV data line based. The separator used and trimming of whitespace
 * for fields are configured on construction.
 */
public class CommaSeparatedValuesReader
	implements StringArrayReader
{
	private final BufferedReader _in;
	private final char _separator;
	private final boolean _trimFields;

	/**
	 * Creates a reader for CSV data using the underlaying reader.
	 * @param in the underlaying reader
	 * @param separator the separator of the fields in a line
	 * @param trimFields whether the field values will be trimmed
	 */
	public CommaSeparatedValuesReader(Reader in, char separator, boolean trimFields)
	{
		_in = in instanceof BufferedReader ? (BufferedReader)in : new BufferedReader(in);
		_separator = separator;
		_trimFields = trimFields;
	}
	
	
	/**
	 * Reads the next line and parse it as CSV data.
	 * @see BufferedReader#readLine()
	 */
	public String[] read() throws IOException
	{
		String line = _in.readLine();
		return line == null ? null : parseLine(line);
	}

	/**
	 * Closes the underlaying reader.
	 */
	public void close() throws IOException
	{
		_in.close();
	}


	private String[] parseLine(String line) throws IOException
	{
		CharBuffer elem = CharBuffer.allocate(line.length());
		List<String> data = new ArrayList<String>();
		boolean quoted = false;
		boolean isQuote = false;
		for (char c : line.toCharArray()) {
			if (!quoted && c == _separator) {
				data.add(getValue(elem));
			} else if (c == CommaSeparatedValues.QUOTE) {
				if (isQuote) {
					elem.append(c);
					isQuote = false;
				} else if (quoted) {
					isQuote = true;
				} else if (elem.position() == 0) {
					quoted = true;
				} else {
					throw new IOException("CSV need quoting when containing quote: " + line);
				}
			} else if (!_trimFields || !Character.isSpaceChar(c) || elem.position() > 0) {
				elem.append(c);
			}
		}
		if (quoted && !isQuote) {
			throw new IOException("Missing end of quoting: " + line);
		}
		data.add(getValue(elem));
		return data.toArray(new String[data.size()]);
	}
	
	
	private static String getValue(CharBuffer value)
	{
		int length = value.position();
		value.rewind();
		value.limit(length);
		try {
			return value.toString();
		} finally {
			value.clear();
		}
	}

}
