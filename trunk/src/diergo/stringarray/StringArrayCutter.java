package diergo.stringarray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A writer to write selected fields of a string arrays.
 */
public class StringArrayCutter implements ArrayWriter<String>
{
	private final ArrayWriter<String> _out;
	private final int[] _fields;
	
	public StringArrayCutter(ArrayWriter<String> out, int[] fields)
	{
		_out = out;
		_fields = fields;
	}

	public void write(String[] values)
		throws IOException
	{
		List<String> result = new ArrayList<String>(values.length);
		for (int i : _fields) {
			if (values.length > i) {
				result.add(values[i]);
			}
		}
		_out.write(result.toArray(new String[result.size()]));
	}

	public void close()
		throws IOException
	{
		_out.close();
	}

}
