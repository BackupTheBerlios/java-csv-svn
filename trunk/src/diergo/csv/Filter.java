package diergo.csv;

import java.io.IOException;
import java.util.Map;

import diergo.array.ArrayWriter;

/**
 * Filter lines from csv data containing a field value.
 */
class Filter
    implements Command
{
  /**
   * Processes csv data by filtering lines with a specified value in on field.
   * The field index is passed in option {@code field} starting at 1, the value
   * is got from option {@code value}. The first line will be returned anyway if
   * the option {@code header} is {@code true}.
   */
  public void process(Iterable<String[]> in, ArrayWriter<String> out, Map<String, String> options)
      throws IOException
  {
    boolean header = Boolean.parseBoolean(options.get("header"));
    String field = options.get("field");
    int i = Integer.parseInt(field) - 1;
    String value = options.get("value");
    for (String[] line : in) {
      if (header) {
        header = false;
      } else if (line.length < i || !line[i].equals(value)) {
        continue;
      }
      out.write(line);
    }
  }
}
