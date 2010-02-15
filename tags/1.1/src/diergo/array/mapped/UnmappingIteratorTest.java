package diergo.array.mapped;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.Test;

public class UnmappingIteratorTest
{
    @Test
    public void fieldsAreReturnedOnEmptyIterator()
    {
        Iterator<String[]> iterator = new UnmappingIterator<Integer>(new String[] { "h1", "h2" }, Collections
                .<Map<String, Integer>>emptyList().iterator());
        assertTrue(iterator.hasNext());
        assertArrayEquals(new String[] { "h1", "h2" }, iterator.next());
    }

    @Test
    public void valuesAreReturnedFromSecondResult()
    {
        Map<String, Integer> values = new HashMap<String, Integer>();
        values.put("h1", 1);
        values.put("h2", 2);
        Iterator<String[]> iterator = new UnmappingIterator<Integer>(new String[] { "h1", "h2" }, Collections
                .singletonList(values).iterator());
        iterator.next();
        assertArrayEquals(new String[] { "1", "2" }, iterator.next());
    }

    @Test
    public void unknownValuesAreReturnedAsNull()
    {
        Map<String, Integer> values = new HashMap<String, Integer>();
        values.put("h1", 1);
        Iterator<String[]> iterator = new UnmappingIterator<Integer>(new String[] { "h1", "h2" }, Collections
                .singletonList(values).iterator());
        iterator.next();
        assertArrayEquals(new String[] { "1", null }, iterator.next());
    }
}
