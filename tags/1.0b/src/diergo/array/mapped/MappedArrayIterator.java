package diergo.array.mapped;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This iterator converts arrays to maps using the first string array as an
 * header with field names.
 * 
 * This class is useful to create input for a {@link ValueTransformer} from a
 * {@link Iterator}.
 */
public class MappedArrayIterator<E>
        implements Iterator<Map<String, E>>
{
    public static MappedArrayIterator<String> createWithHeaders(Iterator<String[]> iterator)
    {
        return new MappedArrayIterator<String>(iterator.next(), iterator);
    }

    private final String[] _header;
    private final Iterator<E[]> _iterator;

    public MappedArrayIterator(String[] header, Iterator<E[]> iterator)
    {
        _header = header;
        _iterator = iterator;
    }

    public boolean hasNext()
    {
        return _iterator.hasNext();
    }

    public Map<String, E> next()
    {
        E[] next = _iterator.next();
        Map<String, E> result = new LinkedHashMap<String, E>();
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
