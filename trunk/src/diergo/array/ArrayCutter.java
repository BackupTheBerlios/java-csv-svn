package diergo.array;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class to cut selected fields of arrays.
 * Cutting array readers and writers are offered.
 */
public class ArrayCutter<E>
{
	/**
	 * Creates a new array reader passing the selected fields only.
	 */
	public static <E> ArrayReader<E> cut(ArrayReader<E> in, int[] fields)
	{
		return new Reader<E>(in, new ArrayCutter<E>(fields));
	}
	
	/**
	 * Creates a new array writer passing the selected fields only.
	 */
	public static <E> ArrayWriter<E> cut(ArrayWriter<E> in, int[] fields)
	{
		return new Writer<E>(in, new ArrayCutter<E>(fields));
	}
	
	private final int[] _fields;
	
	public ArrayCutter(int[] fields)
	{
		_fields = fields;
	}

	/**
	 * Returns a new string array with the selected fields only.
	 * The order will be as definied by the fields, unknown fields are
	 * ignored.
	 */
	public E[] cut(E[] values)
	{
		List<E> result = new ArrayList<E>(values.length);
		for (int i : _fields) {
			if (values.length > i) {
				result.add(values[i]);
			}
		}
		return result.toArray(createArrayAs(values, result.size()));
	}

	@SuppressWarnings("unchecked")
	private E[] createArrayAs(E[] values, int size)
	{
		return (E[]) Array.newInstance(
				values.getClass().getComponentType(), size);
	}
	
	private static class Reader<EE> implements ArrayReader<EE>
	{
		private final ArrayCutter<EE> _cutter;
		private final ArrayReader<EE> _in;

		public Reader(ArrayReader<EE> in, ArrayCutter<EE> cutter)
		{
			_in = in;
			_cutter = cutter;
		}

		public EE[] read()
			throws IOException
		{
			return _cutter.cut(_in.read());
		}

		public void close()
			throws IOException
		{
			_in.close();
		}
		
	}

	
	private static class Writer<EE> implements ArrayWriter<EE>
	{
		private final ArrayWriter<EE> _out;
		private final ArrayCutter<EE> _cutter;

		public Writer(ArrayWriter<EE> out, ArrayCutter<EE> cutter)
		{
			_out = out;
			_cutter = cutter;
		}

		public void write(EE[] values)
			throws IOException
		{
			_out.write(_cutter.cut(values));
		}

		public void close()
			throws IOException
		{
			_out.close();
		}
		
	}
}
