package diergo.csv;

import static diergo.csv.FixedSeparatorDeterminer.fixedSeparator;

import java.io.Writer;

import diergo.array.ArrayLineWriter;

/**
 * Writes CSV data from String arrays to an underlying writer. The separator
 * used is configured on construction.
 */
public class CommaSeparatedValuesWriter
    extends ArrayLineWriter<String>
{
  public static Writer write(Iterable<? extends String[]> source, char separator, Writer out, Option... options)
  {
    return aggregate(source, new CommaSeparatedValuesWriter(out, separator, options));
  }

  /**
   * Creates a writer for CSV data using the underlying writer.
   *
   * @param out
   *          the underlying writer
   * @param separator
   *          the separator of the fields in a line
   */
  public CommaSeparatedValuesWriter(Writer out, char separator, Option... options)
  {
    this(out, fixedSeparator(separator), options);
  }

  public CommaSeparatedValuesWriter(Writer out, SeparatorDeterminer separatorDeterminer, Option... options)
  {
    this(out, new CommaSeparatedValuesGenerator(separatorDeterminer, options));
  }

  public CommaSeparatedValuesWriter(Writer out, CommaSeparatedValuesGenerator parser)
  {
    super(out, parser);
  }
}
