package diergo.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import org.junit.Test;

public class CommaSeparatedValuesTest
{
    @Test
    public void emptyIterableResultsInEmptyString()
    {
        assertEquals("", CommaSeparatedValues.generate(Collections.<String[]>emptyList(), true));
    }

    @Test
    public void eachIterableResultsInOneLine()
    {
        assertEquals(2, CommaSeparatedValues.generate(Arrays.asList(new String[][] { { "1a", "1b" }, { "2a", "2b" } }),
                true).split("\n").length);
    }

    @Test
    public void emptyDataResultsInIteratorWithoutNext()
    {
        assertFalse(CommaSeparatedValues.parse(new StringReader("")).iterator().hasNext());
    }

    @Test
    public void linesAreReturnedByIterator()
    {
        Iterator<String[]> i = CommaSeparatedValues.parse(new StringReader("1\n2\n3")).iterator();
        int idx = 0;
        while (i.hasNext()) {
            assertEquals(String.valueOf(++idx), i.next()[0]);
        }
        assertEquals(3, idx);
    }

}
