package diergo.csv;

import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;

import org.junit.Test;

import diergo.array.test.MockArrayWriter;

public class SortTest
{
    @Test
    public void linesAreSortedByOrderOption()
        throws IOException
    {
        MockArrayWriter<String> out = new MockArrayWriter<String>();
        new Sort().process(new CommaSeparatedValuesReader(new StringReader("2a;2b;2c\n1a;2b;3c\n3a;1b;2c"), ';', true),
                out, Collections.singletonMap("order", "2,3"));
        assertArrayEquals(new String[] { "3a", "1b", "2c" }, out.getResult().get(0));
        assertArrayEquals(new String[] { "2a", "2b", "2c" }, out.getResult().get(1));
        assertArrayEquals(new String[] { "1a", "2b", "3c" }, out.getResult().get(2));
    }

    @Test
    public void defaultUsesFirstFieldAsOrderWithoutHeader()
        throws IOException
    {
        MockArrayWriter<String> out = new MockArrayWriter<String>();
        new Sort().process(new CommaSeparatedValuesReader(new StringReader("2a;2b\n1a;1b"), ';', true), out,
                Collections.<String, String>emptyMap());
        assertArrayEquals(new String[] { "1a", "1b" }, out.getResult().get(0));
        assertArrayEquals(new String[] { "2a", "2b" }, out.getResult().get(1));
    }

    @Test
    public void numericOrderIsUsedOnOptionFieldsEndingWithN()
        throws IOException
    {
        MockArrayWriter<String> out = new MockArrayWriter<String>();
        new Sort().process(new CommaSeparatedValuesReader(new StringReader("1;10\n2;2"), ';', true), out,
                Collections.singletonMap("order", "2n"));
        assertArrayEquals(new String[] { "2", "2" }, out.getResult().get(0));
        assertArrayEquals(new String[] { "1", "10" }, out.getResult().get(1));
    }

    @Test
    public void headerIsIgnoredIfOptionSet()
        throws IOException
    {
        MockArrayWriter<String> out = new MockArrayWriter<String>();
        new Sort().process(new CommaSeparatedValuesReader(new StringReader("2a;2b\n1a;1b"), ';', true), out,
                Collections.singletonMap("header", "true"));
        assertArrayEquals(new String[] { "2a", "2b" }, out.getResult().get(0));
        assertArrayEquals(new String[] { "1a", "1b" }, out.getResult().get(1));
    }
}
