package diergo.array;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class to cut selected fields of a string arrays.
 * Cutting string array readers and writers are offered.
 */
public class StringArrayCutter
{
	/**
	 * Creates a new string array reader passing the selected fields only.
	 */
	public static ArrayReader<String> cut(ArrayReader<String> in, int[] fields)
	{
		return new StringArrayCutter(fields).new Reader(in);
	}
	
	/**
	 * Creates a new string array writer passing the selected fields only.
	 */
	public static ArrayWriter<String> cut(ArrayWriter<String> in, int[] fields)
	{
		return new StringArrayCutter(fields).new Writer(in);
	}
	
	private final int[] _fields;
	
	public StringArrayCutter(int[] fields)
	{
		_fields = fields;
	}

	/**
	 * Returns a new string array with the selected fields only.
	 * The order will be as definied by the fields, unknown fields are
	 * ignored.
	 */
	public String[] cut(String[] values)
	{
		List<String> result = new ArrayList<String>(values.length);
		for (int i : _fields) {
			if (values.length > i) {
				result.add(values[i]);
			}
		}
		return result.toArray(new String[result.size()]);
	}
	
	private class Reader implements ArrayReader<String>
	{
		private final ArrayReader<String> _in;

		public Reader(ArrayReader<String> in)
		{
			_in = in;
		}

		public String[] read()
			throws IOException
		{
			return StringArrayCutter.this.cut(_in.read());
		}

		public void close()
			throws IOException
		{
			_in.close();
		}
		
	}

	
	private class Writer implements ArrayWriter<String>
	{
		private final ArrayWriter<String> _out;

		public Writer(ArrayWriter<String> out)
		{
			_out = out;
		}

		public void write(String[] values)
			throws IOException
		{
			_out.write(StringArrayCutter.this.cut(values));
		}

		public void close()
			throws IOException
		{
			_out.close();
		}
		
	}
}
