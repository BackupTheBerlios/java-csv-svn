package diergo.csv;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.Iterator;

import org.junit.Test;

public class CommaSeparatedValuesParserTest
{
	@Test
	public void emptyDataResultsInIteratorWithoutNext()
	{
		assertFalse(CommaSeparatedValues.parse("", true).iterator().hasNext());
	}
	
	@Test
	public void lineWithoutSeparatorResultsInSingleString()
	{
		String[] data = CommaSeparatedValues.parse("\n", true).iterator().next();
		assertThat(data.length, equalTo(1));
		assertThat(data[0], equalTo(""));
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
	
	@Test
	public void separatedLineIsSplitted()
	{
		String[] data = CommaSeparatedValues.parse("a;b;c", true).iterator().next();
		assertThat(data.length, equalTo(3));
		assertThat(data[0], equalTo("a"));
		assertThat(data[1], equalTo("b"));
		assertThat(data[2], equalTo("c"));
	}
	
	@Test
	public void quotedFieldWithSeparatorIsNotSplitted()
	{
		String[] data = CommaSeparatedValues.parse("\"hi;ho\"", true).iterator().next();
		assertThat(data.length, equalTo(1));
		assertThat(data[0], equalTo("hi;ho"));
	}
	
	@Test
	public void quotedFieldWithQuotesIsUnquoted()
	{
		String[] data = CommaSeparatedValues.parse("\"\"\"hi\"\"ho\"\"\"", true).iterator().next();
		assertThat(data[0], equalTo("\"hi\"ho\""));
	}

	
	@Test(expected=IllegalArgumentException.class)
	public void unquotedFieldWithQuotesIsIllegal()
	{
		CommaSeparatedValues.parse("hi\"ho", true).iterator().next();
	}

	
	@Test(expected=IllegalArgumentException.class)
	public void quotedFieldWithMissingEndQuoteIsIllegal()
	{
		CommaSeparatedValues.parse("\"hi;ho", true).iterator().next();
	}
}
