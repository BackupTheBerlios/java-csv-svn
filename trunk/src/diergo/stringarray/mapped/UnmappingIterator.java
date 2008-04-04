package diergo.stringarray.mapped;

import java.util.Iterator;
import java.util.Map;

import diergo.stringarray.ArrayWriter;

/**
 * This iterator converts maps to string arrays containing a set of
 * specified fields.
 * 
 * This class is useful to pass output from a {@link ValueTransformer}
 * to a {@link ArrayWriter}.
 */
public class UnmappingIterator implements Iterator<String[]>
{
	private final Iterator<Map<String, String>> _iterator;
	private final String[] _fields;
	private boolean _header;

	public UnmappingIterator(String[] fields, Iterator<Map<String, String>> iterator)
	{
		_iterator = iterator;
		_fields = fields;
		_header = true;
	}

	public boolean hasNext()
	{
		return _header ? true : _iterator.hasNext();
	}

	public String[] next()
	{
		if (_header) {
			_header = false;
			return _fields;
		} else {
			Map<String, String> values = _iterator.next();
			String[] result = new String[_fields.length];
			int i = 0;
			for (String key : _fields) {
				result[i++] = values.get(key);
			}
			return result;
		}
	}

	public void remove()
	{
		_iterator.remove();
	}

}
