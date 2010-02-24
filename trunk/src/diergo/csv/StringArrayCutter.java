package diergo.csv;

import diergo.array.ArrayCutter;
import diergo.array.ArrayReader;
import diergo.array.ArrayWriter;

/**
 * A utility class to cut selected fields of string arrays. Cutting string array
 * readers and writers are offered.
 */
public class StringArrayCutter
    extends ArrayCutter<String>
{
  /**
   * Creates a new string array reader passing the selected fields only.
   */
  public static ArrayReader<String> cut(ArrayReader<String> in, int[] fields)
  {
    return ArrayCutter.cut(in, fields);
  }

  /**
   * Creates a new string array writer passing the selected fields only.
   */
  public static ArrayWriter<String> cut(ArrayWriter<String> in, int[] fields)
  {
    return ArrayCutter.cut(in, fields);
  }

  public StringArrayCutter(int[] fields)
  {
    super(fields);
  }

}
