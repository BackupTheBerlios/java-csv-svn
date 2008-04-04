package diergo.stringarray;

import java.io.IOException;

/**
 * A reader to read parsed string arrays.
 */
public interface StringArrayReader
{
	/**
	 * Read the next string array.
	 * @return the arrays read or {@code null} if end on input reached.
	 */
	public String[] read() throws IOException;

	/**
	 * Closes the reader.
	 * Subsequent calls to {@link #read()} will result in an exception.
	 */
	public void close() throws IOException;
}
