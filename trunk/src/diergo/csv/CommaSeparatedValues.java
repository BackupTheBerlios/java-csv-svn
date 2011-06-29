package diergo.csv;

import static diergo.csv.CommaSeparatedValuesReader.read;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Convenience access to {@link CommaSeparatedValuesReader} and
 * {@link CommaSeparatedValuesWriter}.
 */
public class CommaSeparatedValues
{
  /**
   * Parses the data using one of the default separators.
   *
   * @see CommaSeparatedValuesReader#read(Reader)
   */
  public static Iterable<String[]> parse(Reader csvData, Option... options)
  {
    return read(csvData, options);
  }

  /**
   * Parses the data using the passed separator.
   *
   * @see CommaSeparatedValuesReader#read(Reader, char)
   */
  public static Iterable<String[]> parse(Reader csvData, char separator, Option... options)
  {
    return read(csvData, separator, options);
  }

  /**
   * Generates data using the passed separator.
   *
   * @see #generate(Iterable, char, Writer)
   */
  public static String generate(Iterable<String[]> data, char separator, Option... options)
  {
    StringWriter out = new StringWriter();
    try {
      generate(data, separator, out, options);
    } catch (IOException e) {
      throw new AssertionError(e.getMessage());
    }
    return out.toString();
  }

  /**
   * Generates data to the writer using the passed separator.
   */
  public static void generate(Iterable<String[]> data, char separator, Writer out, Option... options)
      throws IOException
  {
    CommaSeparatedValuesWriter generator = new CommaSeparatedValuesWriter(out, separator, options);
    for (String[] line : data) {
      generator.write(line);
    }
  }
}
