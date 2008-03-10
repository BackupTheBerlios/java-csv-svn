package diergo.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

public class CommaSeparatedValuesGeneratorTest
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
	public void eachElementIsSeparated()
	{
		assertThat(CommaSeparatedValues.generate(Arrays.asList(new String[][] {{"a", "b", "c"}}), true).split("\n")[0], is("a;b;c"));
	}

	@Test
	public void elementWithSeparatorIsQuoted()
	{
		assertThat(CommaSeparatedValues.generate(Arrays.asList(new String[][] {{"a", "b;b", "c"}}), true).split("\n")[0], is("a;\"b;b\";c"));
	}

	@Test
	public void elementWithQuoteIsQuoted()
	{
		assertThat(CommaSeparatedValues.generate(Arrays.asList(new String[][] {{"a", "b\"b", "c"}}), true).split("\n")[0], is("a;\"b\"\"b\";c"));
	}
}
