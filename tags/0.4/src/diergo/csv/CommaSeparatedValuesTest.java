package diergo.csv;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import org.junit.Test;

public class CommaSeparatedValuesTest
{
	@Test
	public void emptyIterableResultsInEmptyString()
	{
		assertThat(CommaSeparatedValues.generate(Collections.<String[]>emptyList(), true), is(""));
	}

	@Test
	public void eachIterableResultsInOneLine()
	{
		assertThat(CommaSeparatedValues.generate(Arrays.asList(new String[][] {{"1a", "1b"}, {"2a", "2b"}}), true).split("\n").length, is(2));
	}

	@Test
	public void emptyDataResultsInIteratorWithoutNext()
	{
		assertFalse(CommaSeparatedValues.parse("", true).iterator().hasNext());
	}
	
	@Test
	public void linesAreReturnedByIterator()
	{
		Iterator<String[]> i = CommaSeparatedValues.parse("1\n2\n3", true).iterator();
		int idx = 0;
		while (i.hasNext()) {
			String[] data = i.next();
			assertThat(data[0], equalTo(String.valueOf(++idx)));
		}
		assertThat(idx, is(3));
	}
	
}
