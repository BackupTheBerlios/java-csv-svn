package diergo.csv;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

public class CommaSeparatedValuesReaderTest
{
	@Test
	public void lineWithoutSeparatorResultsInSingleString() throws IOException
	{
		String[] data = new CommaSeparatedValuesReader(new StringReader("\n"), ';', true).read();
		assertThat(data.length, equalTo(1));
		assertThat(data[0], equalTo(""));
	}
	
	@Test
	public void separatedLineIsSplitted() throws IOException
	{
		String[] data = new CommaSeparatedValuesReader(new StringReader("a;b;c"), ';', true).read();
		assertThat(data.length, equalTo(3));
		assertThat(data[0], equalTo("a"));
		assertThat(data[1], equalTo("b"));
		assertThat(data[2], equalTo("c"));
	}
	
	@Test
	public void quotedFieldWithSeparatorIsNotSplitted() throws IOException
	{
		String[] data = new CommaSeparatedValuesReader(new StringReader("\"hi;ho\""), ';', true).read();
		assertThat(data.length, equalTo(1));
		assertThat(data[0], equalTo("hi;ho"));
	}
	
	@Test
	public void quotedFieldWithQuotesIsUnquoted() throws IOException
	{
		String[] data = new CommaSeparatedValuesReader(new StringReader("\"\"\"hi\"\"ho\"\"\""), ';', true).read();
		assertThat(data[0], equalTo("\"hi\"ho\""));
	}

	
	@Test(expected=IOException.class)
	public void unquotedFieldWithQuotesIsIllegal() throws IOException
	{
		new CommaSeparatedValuesReader(new StringReader("hi\"ho"), ';', true).read();
	}

	
	@Test(expected=IOException.class)
	public void quotedFieldWithMissingEndQuoteIsIllegal() throws IOException
	{
		new CommaSeparatedValuesReader(new StringReader("\"hi;ho"), ';', true).read();
	}
	
	@Test
	public void iteratorReturnsSameAsRead() throws IOException
	{
		BufferedReader in = new BufferedReader(new StringReader("a;b;c"), 6);
		CommaSeparatedValuesReader reader = new CommaSeparatedValuesReader(in, ';', true);
		in.mark(6);
		String[] dataRead = reader.read();
		in.reset();
		assertThat(reader.iterator().next(), equalTo(dataRead));
	}
}
