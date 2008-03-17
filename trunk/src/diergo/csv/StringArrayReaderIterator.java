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

	/**
	 * Tries to read one more line if not read yet.
	 * @return whether a line has been read now or before
	 * @see StringArrayReader#read()
	 */
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

	/**
	 * Returns the next line parsed.
	 * @see {@link #hasNext()}
	 */
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

	/**
	 * Removal is not supported.
	 * @throws UnsupportedOperationException
	 */
	public void remove()
	{
		throw new UnsupportedOperationException("Comma separated value source cannot be modified");
	}
	
}