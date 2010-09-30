package diergo.csv;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import diergo.util.transform.Transformer;

/**
 * The real parsing methods.
 *
 * @since 1.1
 */
public class CommaSeparatedValuesParser
    implements Transformer<String, String[]>, DelegatingSeparatorDeterminer.SeparatorProvider
{
  public static char QUOTE = '"';
  private static final String[] EMPTY_LINE = new String[0];
  private final SeparatorDeterminer _determiner;
  private final EnumSet<Option> _options;
  private Character separator;

  public CommaSeparatedValuesParser(SeparatorDeterminer determiner, Option... options)
  {
    _options = EnumSet.noneOf(Option.class);
    for (Option option : options) {
      _options.add(option);
    }
    _determiner = determiner;
  }

  public String[] transform(String line)
  {
    if (isEmpty(line)) {
      return EMPTY_LINE;
    }
    if (separator == null) {
      separator = _determiner.determineSeparator(line);
    }
    CharBuffer elem = CharBuffer.allocate(line.length());
    List<String> data = new ArrayList<String>();
    boolean quoted = false;
    boolean isQuote = false;
    for (char c : line.toCharArray()) {
      if (!quoted && c == separator) {
        data.add(getValue(elem));
      } else if (c == QUOTE) {
        if (isQuote) {
          elem.append(c);
          isQuote = false;
        } else if (quoted) {
          isQuote = true;
        } else if (elem.position() == 0) {
          quoted = true;
        } else {
          throw new IllegalArgumentException("CSV need quoting when containing quote: " + line);
        }
      } else if (!_options.contains(Option.TRIM) || !Character.isSpaceChar(c) || elem.position() > 0) {
        elem.append(c);
      }
    }
    if (quoted && !isQuote) {
      throw new IllegalArgumentException("Missing end of quoting: " + line);
    }
    data.add(getValue(elem));
    return data.toArray(new String[data.size()]);
  }

  public char getSeparator()
  {
    if (separator == null) {
      throw new IllegalStateException("No line read yet");
    }
    return separator;
  }

  private boolean isEmpty(String line)
  {
    return line == null || line.trim().length() == 0;
  }

  private String getValue(CharBuffer value)
  {
    int length = value.position();
    value.rewind();
    value.limit(length);
    try {
      String result = value.toString();
      if (_options.contains(Option.EMPTY_AS_NULL) && result.length() == 0) {
        return null;
      }
      return result;
    } finally {
      value.clear();
    }
  }

  public enum Option
  {
    TRIM,
    EMPTY_AS_NULL
  }
}
