package diergo.csv;

import diergo.array.ArrayComparator;

/**
 * A special string array comparator to compare a single field in a numeric or
 * case insensitive manner.
 */
public class StringArrayComparator
    extends ArrayComparator<String>
{
  private final int[] _numericFields;

  /**
   * Create a comparator comparing by the specified indices. All fields are
   * treated as strings.
   * 
   * @param fieldOrder
   *          the indices of the fields to be used for comparing
   */
  public StringArrayComparator(int[] fieldOrder)
  {
    this(fieldOrder, new int[0]);
  }

  /**
   * Create a comparator comparing by the specified indices.
   * 
   * @param fieldOrder
   *          the indices of the fields to be used for comparing
   * @param numericFields
   *          the indices of the fields to be compared numeric
   */
  public StringArrayComparator(int[] fieldOrder, int[] numericFields)
  {
    super(fieldOrder);
    _numericFields = numericFields;
  }

  /**
   * Compares the two corresponding values at an index. The values are compared
   * by alpha or numeric as defined by the numeric fields passed on
   * construction.
   * 
   * @see #compareAlpha(String, String)
   * @see #compareNumeric(String, String)
   */
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

  /**
   * Compares the two numeric values.
   */
  protected int compareNumeric(String value1, String value2)
      throws NumberFormatException
  {
    double v1 = Double.parseDouble(value1);
    double v2 = Double.parseDouble(value2);
    return v1 == v2 ? 0 : (v1 < v2 ? -1 : 1);
  }

  /**
   * Compares the two alpha values ignoring case.
   */
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
