package diergo.csv.mapped;

import java.util.Map;

/**
 * A value transformer creates an object of type T from a value map
 * and vice versa.
 */
public interface ValueTransformer<T>
{
	public T transform(Map<String,String> data);
	
	public Map<String,String> transform(T value);
}
