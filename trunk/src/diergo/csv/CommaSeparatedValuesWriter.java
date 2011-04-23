package diergo.csv;

import static diergo.csv.Option.COMMENTS_SKIPPED;
import static diergo.csv.Option.EMPTY_AS_NULL;

import java.io.Writer;

import diergo.array.ArrayLineWriter;

/**
 * Writes CSV data from String arrays to an underlying writer. The separator
 * used is configured on construction.
 */
public class CommaSeparatedValuesWriter
    extends ArrayLineWriter<String>
{
  public static Writer write(Iterable<? extends String[]> source, char separator, Writer out)
  {
    return aggregate(source, new CommaSeparatedValuesWriter(out, separator));
  }

  /**
   * Creates a writer for CSV data using the underlying writer.
   *
   * @param out
   *          the underlying writer
   * @param separator
   *          the separator of the fields in a line
   */
  public CommaSeparatedValuesWriter(Writer out, char separator)
  {
    this(out, new FixedSeparatorDeterminer(separator));
  }

  public CommaSeparatedValuesWriter(Writer out, SeparatorDeterminer separatorDeterminer)
  {
    this(out, new CommaSeparatedValuesGenerator(separatorDeterminer, EMPTY_AS_NULL, COMMENTS_SKIPPED));
  }

  public CommaSeparatedValuesWriter(Writer out, CommaSeparatedValuesGenerator parser)
  {
    super(out, parser);
  }
}
