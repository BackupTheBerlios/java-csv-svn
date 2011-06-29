package diergo.csv;

import static diergo.csv.Option.TRIM;

import java.util.EnumSet;
import java.util.regex.Pattern;

import diergo.util.transform.Transformer;

/**
 * The real generating methods.
 *
 * @since 1.2
 */
public class CommaSeparatedValuesGenerator
    implements Transformer<String[], String>
{
  public static char QUOTE = '"';
  private static final Pattern QUOTE_PATTERN = Pattern.compile(String.valueOf(QUOTE));
  private static final String QUOTE_REPLACEMENT = new String(new char[] { QUOTE, QUOTE });
  private final EnumSet<Option> options;
  private final SeparatorDeterminer determiner;
  private boolean firstGenerated;

  public CommaSeparatedValuesGenerator(SeparatorDeterminer determiner, Option... options)
  {
    this.options = EnumSet.noneOf(Option.class);
    for (Option option : options) {
      this.options.add(option);
    }
    this.determiner = determiner;
    firstGenerated = false;
  }

  public String transform(String[] line)
  {
    if (line.length == 0) {
      return "";
    }
    if (line.length == 1 && line[0].startsWith("#")) {
      return line[0];
    }
    StringBuffer out = new StringBuffer();
    char separator = determiner.determineSeparator(null);
    for (String elem : line) {
      if (out.length() > 0) {
        out.append(separator);
      }
      if (options.contains(TRIM)) {
        if (elem != null) {
          elem = elem.trim();
        }
        out.append(' ');
      }
      out.append(quote(elem, separator));
    }
    if (!firstGenerated && options.contains(Option.COMMENTED_HEADER)) {
      out.insert(0, '#');
    }
    firstGenerated = true;
    return out.toString();
  }

  private String quote(String elem, char separator)
  {
    if (elem == null) {
      return options.contains(Option.EMPTY_AS_NULL) ? "" : "null";
    }
    boolean containsQuote = elem.indexOf(QUOTE) != -1;
    boolean containsNewline = elem.indexOf('\n') != -1 || elem.indexOf('\r') != -1;
    boolean quote = elem.indexOf(separator) != -1 || containsQuote || containsNewline;
    if (quote) {
      if (containsQuote) {
        elem = QUOTE_PATTERN.matcher(elem).replaceAll(QUOTE_REPLACEMENT);
      }
      elem = QUOTE + elem + QUOTE;
    }
    return elem;
  }
}
