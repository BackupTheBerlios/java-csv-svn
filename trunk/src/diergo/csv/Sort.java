package diergo.csv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import diergo.array.ArrayWriter;

/**
 * Sort lines of csv data according to an order.
 */
class Sort
    implements Command
{

  /**
   * Processes csv data by sorting the lines. The field indices are passed in
   * option {@code order} starting at 1. Each index may be followed by an
   * {@code n} to indicate numeric ordering. The first line will be returned as
   * first if the option {@code header} is {@code true}.
   */
  public void process(Iterable<String[]> in, ArrayWriter<String> out, Map<String, String> options)
      throws IOException
  {
    boolean header = Boolean.parseBoolean(options.get("header"));
    List<String[]> lines = new ArrayList<String[]>();
    for (String[] line : in) {
      if (header) {
        out.write(line);
        header = false;
      } else {
        lines.add(line);
      }
    }
    String order = options.get("order");
    if (order == null) {
      order = "1";
    }
    Collections.sort(lines, createComparator(order));
    for (String[] line : lines) {
      out.write(line);
    }
  }

  private static Comparator<String[]> createComparator(String option)
  {
    String[] args = option.split(",");
    int[] fields = new int[args.length];
    List<Integer> num = new ArrayList<Integer>(fields.length);
    int i = 0;
    for (String arg : args) {
      boolean numeric = arg.endsWith("n");
      if (numeric) {
        arg = arg.substring(0, arg.length() - 1);
      }
      int idx = Integer.parseInt(arg) - 1;
      fields[i++] = idx;
      if (numeric) {
        num.add(new Integer(idx));
      }
    }
    int[] numeric = new int[num.size()];
    i = 0;
    for (Integer n : num) {
      numeric[i++] = n.intValue();
    }
    return new StringArrayComparator(fields, numeric);
  }
}
