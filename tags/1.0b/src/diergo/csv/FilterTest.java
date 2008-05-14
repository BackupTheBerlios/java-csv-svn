package diergo.csv;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import diergo.array.test.MockArrayWriter;

public class FilterTest
{
	@Test
	public void fieldIsFilteredByIndex()
		throws IOException
	{
		Map<String,String> options = new HashMap<String, String>();
		options.put("field", "2");
		options.put("value", "test");
		MockArrayWriter<String> out = new MockArrayWriter<String>();
		new Filter().process(new CommaSeparatedValuesReader(new StringReader("1;bla\n2;test\n3;blub"), ';', true), out, options);
		assertEquals(1, out.getResult().size());
		assertArrayEquals(new String[] {"2", "test"}, out.getResult().get(0));
	}

	@Test
	public void headerIsNotFiltered()
		throws IOException
	{
		Map<String,String> options = new HashMap<String, String>();
		options.put("field", "2");
		options.put("value", "test");
		options.put("header", "true");
		MockArrayWriter<String> out = new MockArrayWriter<String>();
		new Filter().process(new CommaSeparatedValuesReader(new StringReader("1;bla\n2;test\n3;blub"), ';', true), out, options);
		assertEquals(2, out.getResult().size());
		assertArrayEquals(new String[] {"2", "test"}, out.getResult().get(1));
	}
}
