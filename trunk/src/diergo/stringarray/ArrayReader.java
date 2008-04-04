package diergo.stringarray;

import java.io.IOException;

/**
 * A reader to read parsed string arrays.
 */
public interface ArrayReader<E>
{
	/**
	 * Read the next array.
	 * @return the arrays read or {@code null} if end on input reached.
	 */
	public E[] read() throws IOException;

	/**
	 * Closes the reader.
	 * Subsequent calls to {@link #read()} will result in an exception.
	 */
	public void close() throws IOException;
}
