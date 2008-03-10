package diergo.csv;

import java.util.Comparator;


/**
 * Compares CSV lines by a defined order of fields.
 */
public class CammaSeparatedValuesComparator
	implements Comparator<String[]>
{
	private final int[] _fieldOrder;
	private final int[] _numericFields;

	public CammaSeparatedValuesComparator(int[] fieldOrder)
	{
		this(fieldOrder, new int[0]);
	}

	public CammaSeparatedValuesComparator(int[] fieldOrder, int[] numericFields)
	{
		_fieldOrder = fieldOrder;
		_numericFields = numericFields;
	}

	public int compare(String[] line1, String[] line2)
	{
		for (int i = 0; i < _fieldOrder.length; ++i) {
			int f = _fieldOrder[i];
			if (line1.length <= f) {
				return line2.length <= f ? 0 : -1;
			}
			if (line2.length <= f) {
				return line1.length <= f ? 0 : 1;
			}
			int c = compare(line1, line2, f);
			if (c != 0) {
				return c;
			}
		}
		return 0;
	}

	private int compare(String[] line1, String[] line2, int i) {
		int c;
		if (isNumeric(i)) {
			try {
				c = compareNumeric(line1[i], line2[i]);
			} catch (NumberFormatException e) {
				c = compareAlpha(line1[i], line2[i]);
			}
		} else {
			c = compareAlpha(line1[i], line2[i]);
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
