package diergo.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
		assertThat(out.toString().split("\n")[0], is("a;b;c"));
	}

	@Test
	public void elementWithSeparatorIsQuoted() throws IOException
	{
		StringWriter out = new StringWriter();
		new CommaSeparatedValuesWriter(out, ';').write(new String[] {"a", "b;b", "c"});
		assertThat(out.toString().split("\n")[0].split("\n")[0], is("a;\"b;b\";c"));
	}

	@Test
	public void elementWithQuoteIsQuoted() throws IOException
	{
		StringWriter out = new StringWriter();
		new CommaSeparatedValuesWriter(out, ';').write(new String[] {"a", "b\"b", "c"});
		assertThat(out.toString().split("\n")[0].split("\n")[0], is("a;\"b\"\"b\";c"));
	}
}
