package diergo.csv;

import diergo.array.ArrayComparator;

public class StringArrayComparator extends ArrayComparator<String>
{
	private final int[] _numericFields;

	/**
	 * Create a comparator comparing by the specified indicees.
	 * All fields are treated as strings.
	 * @param fieldOrder the indicees of the fields to be used for comparing
	 */
	public StringArrayComparator(int[] fieldOrder)
	{
		this(fieldOrder, new int[0]);
	}

	/**
	 * Create a comparator comparing by the specified indicees.
	 * @param fieldOrder the indicees of the fields to be used for comparing
	 * @param numericFields the indicees of the fields to be compared numeric
	 */
	public StringArrayComparator(int[] fieldOrder, int[] numericFields)
	{
		super(fieldOrder);
		_numericFields = numericFields;
	}

	protected int compare(String value1, String value2, int i)
	{
		int c;
		if (isNumeric(i)) {
			try {
				c = compareNumeric(value1, value2);
			} catch (NumberFormatException e) {
				c = compareAlpha(value1, value2);
			}
		} else {
			c = compareAlpha(value1, value2);
		}
		if (c != 0) {
			return c;
		}
		return c;
	}
	
	protected int compareNumeric(String value1, String value2)
		throws NumberFormatException
	{
		double v1 = Double.parseDouble(value1);
		double v2 = Double.parseDouble(value2);
		return  v1 == v2 ? 0 : (v1 < v2 ? -1 : 1);
	}

	protected int compareAlpha(String value1, String value2)
	{
		return value1.compareToIgnoreCase(value2);
	}
	
	private boolean isNumeric(int field)
	{
		for (int i = 0; i < _numericFields.length; ++i) {
			if (_numericFields[i] == field) {
				return true;
			}
		}
		return false;
	}

}
