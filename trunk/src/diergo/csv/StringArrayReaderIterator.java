package diergo.csv;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterator operating on a reader returning string arrays.
 */
public class StringArrayReaderIterator
	implements Iterator<String[]>
{
	private final StringArrayReader _reader;
	private String[] _nextLine;

	public StringArrayReaderIterator(StringArrayReader reader)
	{
		_reader = reader;
	}

	public boolean hasNext()
	{
		if (_nextLine == null) {
			try {
				_nextLine = _reader.read();
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