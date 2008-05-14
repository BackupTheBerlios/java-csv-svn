package diergo.csv;

import static org.junit.Assert.assertArrayEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

public class CommaSeparatedValuesReaderTest
{
    @Test
    public void lineWithoutSeparatorResultsInSingleString()
        throws IOException
    {
        String[] data = new CommaSeparatedValuesReader(new StringReader("\n"), ';', true).read();
        assertArrayEquals(new String[] { "" }, data);
    }

    @Test
    public void separatedLineIsSplitted()
        throws IOException
    {
        String[] data = new CommaSeparatedValuesReader(new StringReader("a;b;c"), ';', true).read();
        assertArrayEquals(new String[] { "a", "b", "c" }, data);
    }

    @Test
    public void quotedFieldWithSeparatorIsNotSplitted()
        throws IOException
    {
        String[] data = new CommaSeparatedValuesReader(new StringReader("\"hi;ho\""), ';', true).read();
        assertArrayEquals(new String[] { "hi;ho" }, data);
    }

    @Test
    public void quotedFieldWithQuotesIsUnquoted()
        throws IOException
    {
        String[] data = new CommaSeparatedValuesReader(new StringReader("\"\"\"hi\"\"ho\"\"\""), ';', true).read();
        assertArrayEquals(new String[] { "\"hi\"ho\"" }, data);
    }

    @Test(expected = IOException.class)
    public void unquotedFieldWithQuotesIsIllegal()
        throws IOException
    {
        new CommaSeparatedValuesReader(new StringReader("hi\"ho"), ';', true).read();
    }

    @Test(expected = IOException.class)
    public void quotedFieldWithMissingEndQuoteIsIllegal()
        throws IOException
    {
        new CommaSeparatedValuesReader(new StringReader("\"hi;ho"), ';', true).read();
    }

    @Test
    public void iteratorReturnsSameAsRead()
        throws IOException
    {
        BufferedReader in = new BufferedReader(new StringReader("a;b;c"), 6);
        CommaSeparatedValuesReader reader = new CommaSeparatedValuesReader(in, ';', true);
        in.mark(6);
        String[] dataRead = reader.read();
        in.reset();
        assertArrayEquals(dataRead, reader.iterator().next());
    }
}
