package diergo.csv;

import java.io.IOException;
import java.util.Map;

import diergo.array.ArrayCutter;
import diergo.array.ArrayWriter;

/**
 * Cut one ore more fields selected from csv data.
 */
public class Cut
    implements Command
{
  /**
   * Processes csv data by passing selected fields. The field indices are passed
   * in option {@code fields} starting at 1.
   */
  public void process(Iterable<String[]> in, ArrayWriter<String> out, Map<String, String> options)
      throws IOException
  {
    String fields = options.get("fields");
    if (fields == null) {
      fields = "1";
    }
    in = createCutter(in, fields);
    for (String[] line : in) {
      out.write(line);
    }
  }

  private static Iterable<String[]> createCutter(Iterable<String[]> in, String option)
  {
    String[] args = option.split(",");
    int[] fields = new int[args.length];
    int i = 0;
    for (String arg : args) {
      fields[i++] = Integer.parseInt(arg) - 1;
    }
    return ArrayCutter.cut(in, fields);
  }
}
