package diergo.csv;

import java.util.Map;

public interface ValueTransformer<T>
{
	public T transform(Map<String,String> data);
	
	public Map<String,String> transform(T value);
}
