package diergo.csv;

import java.util.regex.Pattern;

import diergo.array.ArrayLineGenerator;

/**
 * The real parsing methods.
 * 
 * @since 1.2
 */
public class CommaSeparatedValuesGenerator
    implements ArrayLineGenerator<String>
{
  public static char QUOTE = '"';

  private static final Pattern QUOTE_PATTERN = Pattern.compile(String.valueOf(QUOTE));
  private static final String QUOTE_REPLACEMENT = new String(new char[] { QUOTE, QUOTE });

  private final SeparatorDeterminer _determiner;

  public CommaSeparatedValuesGenerator(SeparatorDeterminer determiner)
  {
    _determiner = determiner;
  }

  protected boolean isEmpty(String line)
  {
    return line == null || line.trim().length() == 0;
  }

  private String quote(String elem, char separator)
  {
    boolean containsQuote = elem.indexOf(QUOTE) != -1;
    boolean quote = elem.indexOf(separator) != -1 || containsQuote;
    if (quote) {
      if (containsQuote) {
        elem = QUOTE_PATTERN.matcher(elem).replaceAll(QUOTE_REPLACEMENT);
      }
      elem = QUOTE + elem + QUOTE;
    }
    return elem;
  }

  public String generateLine(String... line)
  {
    if (line.length == 0) {
      return "";
    }
    StringBuffer out = new StringBuffer();
    char separator = _determiner.determineSeparator(null);
    for (String elem : line) {
      if (out.length() > 0) {
        out.append(separator);
      }
      out.append(quote(elem, separator));
    }
    return out.toString();
  }
}
