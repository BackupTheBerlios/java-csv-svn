package diergo.array;

import java.util.Comparator;


/**
 * Compares arrays by a defined order of indicees.
 */
public class ArrayComparator<E extends Comparable<E>>
	implements Comparator<E[]>
{
	private final int[] _fieldOrder;

	/**
	 * Create a comparator comparing by the specified indicees.
	 * @param fieldOrder the indicees of the fields to be used for comparing
	 */
	public ArrayComparator(int[] fieldOrder)
	{
		_fieldOrder = fieldOrder;
	}

	/**
	 * Compares the two arrays.
	 * If both arrays contain a field at an index specified by the
	 * field order passed to the constructor, the comparision will be
	 * delegated to {@link #compare(Comparable, Comparable, int)}.
	 */
	public int compare(E[] line1, E[] line2)
	{
		for (int i = 0; i < _fieldOrder.length; ++i) {
			int f = _fieldOrder[i];
			if (line1.length <= f) {
				return line2.length <= f ? 0 : -1;
			}
			if (line2.length <= f) {
				return line1.length <= f ? 0 : 1;
			}
			int c = compare(line1[f], line2[f], f);
			if (c != 0) {
				return c;
			}
		}
		return 0;
	}

	/**
	 * Compares the two corresponding values at an index.
	 * This may be overridden by subclasses to add type specific
	 * comparision.
	 */
	protected int compare(E value1, E value2, int i)
	{
		return value1.compareTo(value2);
	}

}
