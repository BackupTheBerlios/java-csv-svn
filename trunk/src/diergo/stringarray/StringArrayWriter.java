package diergo.stringarray;

import java.io.IOException;

/**
 * A writer to write string arrays to.
 */
public interface StringArrayWriter
{
	/**
	 * Writes the next string array.
	 */
	public void write(String[] values)
		throws IOException;

	/**
	 * Closes the writer.
	 * Subsequent calls to {@link #write(String[])} will result in an exception.
	 */
	public void close() throws IOException;
}
