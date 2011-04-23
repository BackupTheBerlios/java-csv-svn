package diergo.csv;

import static diergo.csv.AutoSeparatorDeterminer.DEFAULT_SEPARATOR_DETERMINER;
import static diergo.csv.Option.COMMENTS_SKIPPED;
import static diergo.csv.Option.EMPTY_AS_NULL;

import java.io.Reader;

import diergo.array.ArrayLineReader;

/**
 * Reads CSV data line based. The separator used is configured on construction.
 */
public class CommaSeparatedValuesReader
    extends ArrayLineReader<String>
    implements DelegatingSeparatorDeterminer.SeparatorProvider
{
  /**
   * Reads CSV data from the underlying reader using the separator passed.
   *
   * @see #CommaSeparatedValuesReader(Reader, char)
   */
  public static Iterable<String[]> read(Reader in, char separator)
  {
    return new CommaSeparatedValuesReader(in, separator);
  }

  /**
   * Reads CSV data from the underlying reader.
   *
   * @see #CommaSeparatedValuesReader(Reader, SeparatorDeterminer)
   * @see AutoSeparatorDeterminer#DEFAULT_SEPARATORS
   */
  public static Iterable<String[]> read(Reader in)
  {
    return new CommaSeparatedValuesReader(in, DEFAULT_SEPARATOR_DETERMINER);
  }

  /**
   * Creates a reader for CSV data using the underlying reader.
   *
   * @param in
   *          the underlying reader
   * @param separator
   *          the separator of the fields in a line
   */
  public CommaSeparatedValuesReader(Reader in, char separator)
  {
    this(in, new FixedSeparatorDeterminer(separator));
  }

  /**
   * Creates a reader for CSV data using the underlying reader.
   *
   * @param in
   *          the underlying reader
   * @param separatorDeterminer
   *          the determiner of the separator of fields in a line
   */
  public CommaSeparatedValuesReader(Reader in, SeparatorDeterminer separatorDeterminer)
  {
    super(in, new CommaSeparatedValuesParser(separatorDeterminer, EMPTY_AS_NULL, COMMENTS_SKIPPED));
  }

  public char getSeparator()
  {
    return ((DelegatingSeparatorDeterminer.SeparatorProvider) _parser).getSeparator();
  }
}
