package diergo.csv;

import static diergo.csv.AutoSeparatorDeterminer.DEFAULT_SEPARATOR_DETERMINER;

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
  public static Iterable<String[]> read(Reader in, char separator, Option... options)
  {
    return new CommaSeparatedValuesReader(in, separator, options);
  }

  /**
   * Reads CSV data from the underlying reader.
   *
   * @see #CommaSeparatedValuesReader(Reader, SeparatorDeterminer)
   * @see AutoSeparatorDeterminer#DEFAULT_SEPARATORS
   */
  public static Iterable<String[]> read(Reader in, Option... options)
  {
    return new CommaSeparatedValuesReader(in, DEFAULT_SEPARATOR_DETERMINER, options);
  }

  /**
   * Creates a reader for CSV data using the underlying reader.
   *
   * @param in
   *          the underlying reader
   * @param separator
   *          the separator of the fields in a line
   */
  public CommaSeparatedValuesReader(Reader in, char separator, Option... options)
  {
    this(in, new FixedSeparatorDeterminer(separator), options);
  }

  /**
   * Creates a reader for CSV data using the underlying reader.
   *
   * @param in
   *          the underlying reader
   * @param separatorDeterminer
   *          the determiner of the separator of fields in a line
   */
  public CommaSeparatedValuesReader(Reader in, SeparatorDeterminer separatorDeterminer, Option... options)
  {
    super(in, new CommaSeparatedValuesParser(separatorDeterminer, options));
  }

  public char getSeparator()
  {
    return ((DelegatingSeparatorDeterminer.SeparatorProvider) _parser).getSeparator();
  }
}
