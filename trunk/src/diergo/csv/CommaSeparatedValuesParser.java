package diergo.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Parses for CSV data. The separator used and trimming of whitespaces
 * for fields are configured on construction.
 */
public class CommaSeparatedValuesParser
{
	private final char _separator;
	final boolean _trimFields;

	public CommaSeparatedValuesParser(final char separator, boolean trimFields)
	{
		_separator = separator;
		_trimFields = trimFields;
	}

	public Iterator<String[]> parse(Reader csvData)
	{
		return new CsvInterator(csvData);
	}

	private String[] parseLine(String line)
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
					throw new IllegalArgumentException("CSV need quoting when containing quote: " + line);
				}
			} else if (!_trimFields || !Character.isSpaceChar(c) || elem.position() > 0) {
				elem.append(c);
			}
		}
		if (quoted && !isQuote) {
			throw new IllegalArgumentException("Missing end of quoting: " + line);
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

	
	private class CsvInterator
		implements Iterator<String[]>
	{
		private final BufferedReader _csvReader;
		private String _nextLine;

		public CsvInterator(Reader csvData)
		{
			_csvReader = (csvData instanceof BufferedReader) ? (BufferedReader)csvData : new BufferedReader(csvData);
		}

		public boolean hasNext()
		{
			if (_nextLine == null) {
				try {
					_nextLine = _csvReader.readLine();
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
				return CommaSeparatedValuesParser.this.parseLine(_nextLine);
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
