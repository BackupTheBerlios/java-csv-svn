package diergo.stringarray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class StringArrayCutterTest
{
	@Test
	public void onlySelectedValuesAreReturned() throws IOException
	{
		TestWriter out = new TestWriter();
		new StringArrayCutter(out, new int[] {1,3}).write(new String[] {"0", "1", "2", "3"});
		assertArrayEquals(new String[] {"1", "3"}, out.getResult().get(0));
	}
	
	@Test
	public void fieldsTooLargeAreIgnored() throws IOException
	{
		TestWriter out = new TestWriter();
		new StringArrayCutter(out, new int[] {1,3}).write(new String[] {"0", "1"});
		assertArrayEquals(new String[] {"1"}, out.getResult().get(0));
	}
	
	@Test
	public void fieldsAreRearranged() throws IOException
	{
		TestWriter out = new TestWriter();
		new StringArrayCutter(out, new int[] {2,1,0}).write(new String[] {"0", "1", "2"});
		assertArrayEquals(new String[] {"2", "1", "0"}, out.getResult().get(0));
	}
	
	private class TestWriter implements ArrayWriter<String>
	{
		private final List<String[]> _out = new ArrayList<String[]>();

		public void write(String[] values) throws IOException {
			_out.add(values);
		}

		public void close()
		{
		}
		
		public List<String[]> getResult()
		{
			return _out;
		}
		
	}
}
