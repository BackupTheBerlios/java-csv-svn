package diergo.csv;

import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;

import org.junit.Test;

import diergo.array.test.MockArrayWriter;

public class CutTest
{
    @Test
    public void cutFieldsPassedAsOption()
        throws IOException
    {
        MockArrayWriter<String> out = new MockArrayWriter<String>();
        new Cut().process(new CommaSeparatedValuesReader(new StringReader("1a;1b;1c\n2a;2b;2c"), ';', true), out,
                Collections.singletonMap("fields", "1,3"));
        assertArrayEquals(new String[] { "1a", "1c" }, out.getResult().get(0));
        assertArrayEquals(new String[] { "2a", "2c" }, out.getResult().get(1));
    }

    @Test
    public void defaultCutsFirstField()
        throws IOException
    {
        MockArrayWriter<String> out = new MockArrayWriter<String>();
        new Cut().process(new CommaSeparatedValuesReader(new StringReader("1a;1b;1c\n2a;2b;2c"), ';', true), out,
                Collections.<String, String>emptyMap());
        assertArrayEquals(new String[] { "1a" }, out.getResult().get(0));
        assertArrayEquals(new String[] { "2a" }, out.getResult().get(1));
    }
}
