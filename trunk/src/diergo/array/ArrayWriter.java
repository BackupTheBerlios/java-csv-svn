package diergo.array;

import java.io.IOException;

/**
 * A writer to write string arrays to.
 */
public interface ArrayWriter<E>
{
	/**
	 * Writes the next array.
	 */
	public void write(E[] values)
		throws IOException;

	/**
	 * Closes the writer.
	 * Subsequent calls to {@link #write(Object[])} will result in an exception.
	 */
	public void close() throws IOException;
}
