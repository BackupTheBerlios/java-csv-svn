package diergo.csv;

import static diergo.csv.AutoSeparatorDeterminer.DEFAULT_SEPARATOR_DETERMINER;

import java.io.Reader;

import diergo.array.ArrayLineReader;

/**
 * Reads CSV data line based. The separator used and trimming of whitespace for
 * fields are configured on construction.
 */
public class CommaSeparatedValuesReader
    extends ArrayLineReader<String>
    implements DelegatingSeparatorDeterminer.SeparatorProvider
{
  /**
   * Reads CSV data from the underlying reader trimming the fields.
   *
   * @see #CommaSeparatedValuesReader(Reader, char, boolean)
   */
  public static Iterable<String[]> read(Reader in, char separator)
  {
    return new CommaSeparatedValuesReader(in, separator, true);
  }

  /**
   * Reads CSV data from the underlying reader trimming the fields.
   *
   * @see #CommaSeparatedValuesReader(Reader, SeparatorDeterminer , boolean)
   * @see AutoSeparatorDeterminer#DEFAULT_SEPARATORS
   */
  public static Iterable<String[]> read(Reader in)
  {
    return new CommaSeparatedValuesReader(in, DEFAULT_SEPARATOR_DETERMINER, true);
  }

  /**
   * Creates a reader for CSV data using the underlying reader.
   *
   * @param in
   *          the underlying reader
   * @param separator
   *          the separator of the fields in a line
   * @param trimFields
   *          whether the field values will be trimmed
   */
  public CommaSeparatedValuesReader(Reader in, char separator, boolean trimFields)
  {
    this(in, new FixedSeparatorDeterminer(separator), trimFields);
  }

  /**
   * Creates a reader for CSV data using the underlying reader.
   *
   * @param in
   *          the underlying reader
   * @param separatorDeterminer
   *          the determiner of the separator of fields in a line
   * @param trimFields
   *          whether the field values will be trimmed
   */
  public CommaSeparatedValuesReader(Reader in, SeparatorDeterminer separatorDeterminer, boolean trimFields)
  {
    super(in, new CommaSeparatedValuesParser(separatorDeterminer,
        trimFields ? new CommaSeparatedValuesParser.Option[] { CommaSeparatedValuesParser.Option.TRIM }
            : new CommaSeparatedValuesParser.Option[0]));
  }

  public char getSeparator()
  {
    return ((DelegatingSeparatorDeterminer.SeparatorProvider) _parser).getSeparator();
  }
}
