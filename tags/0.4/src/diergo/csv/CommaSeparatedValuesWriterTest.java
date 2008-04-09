package diergo.csv;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Test;

public class CommaSeparatedValuesWriterTest
{
	@Test
	public void eachElementIsSeparated() throws IOException
	{
		StringWriter out = new StringWriter();
		new CommaSeparatedValuesWriter(out, ';').write(new String[] {"a", "b", "c"});
		assertEquals("a;b;c", out.toString().split("\n")[0]);
	}

	@Test
	public void elementWithSeparatorIsQuoted() throws IOException
	{
		StringWriter out = new StringWriter();
		new CommaSeparatedValuesWriter(out, ';').write(new String[] {"a", "b;b", "c"});
		assertEquals("a;\"b;b\";c", out.toString().split("\n")[0].split("\n")[0]);
	}

	@Test
	public void elementWithQuoteIsQuoted() throws IOException
	{
		StringWriter out = new StringWriter();
		new CommaSeparatedValuesWriter(out, ';').write(new String[] {"a", "b\"b", "c"});
		assertEquals("a;\"b\"\"b\";c", out.toString().split("\n")[0].split("\n")[0]);
	}
}
