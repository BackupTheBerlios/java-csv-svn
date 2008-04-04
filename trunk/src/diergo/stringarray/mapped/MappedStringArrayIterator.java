package diergo.stringarray.mapped;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * This iterator converts string arrays to maps using the first string
 * array as an header with field names.
 * 
 * This class is useful to create input for a {@link ValueTransformer}
 * from a {@link Iterator}.
 */
public class MappedStringArrayIterator
	implements Iterator<Map<String, String>>
{
	private final String[] _header;
	private final Iterator<String[]> _iterator;

	public MappedStringArrayIterator(String[] header, Iterator<String[]> iterator)
	{
		_header = header;
		_iterator = iterator;
	}

	public MappedStringArrayIterator(Iterator<String[]> iterator)
	{
		this(iterator.next(), iterator);
	}

	public boolean hasNext()
	{
		return _iterator.hasNext();
	}

	public Map<String, String> next()
	{
		String[] next = _iterator.next();
		Map<String,String> result = new LinkedHashMap<String, String>();
		for (int i = 0; i < _header.length; ++i) {
			result.put(_header[i], i < next.length ? next[i] : null);
		}
		return result;
	}

	public void remove()
	{
		_iterator.remove();
	}

}
