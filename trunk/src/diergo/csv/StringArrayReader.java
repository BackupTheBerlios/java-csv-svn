package diergo.csv;

import java.io.IOException;

public interface StringArrayReader
{
	public String[] read() throws IOException;

	public void close() throws IOException;
}
