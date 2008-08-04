package diergo.csv;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import diergo.array.LineParser;

/**
 * The real parsing methods.
 * @since 1.1
 */
public class CommaSeparatedValuesParser implements LineParser<String>
{
    public static char QUOTE = '"';

    private static final Pattern QUOTE_PATTERN = Pattern.compile(String.valueOf(QUOTE));
    private static final String QUOTE_REPLACEMENT = new String(new char[] {QUOTE, QUOTE});

    private char _separator;
    private SeparatorDeterminer _determiner;
    private final boolean _trimFields;

    public CommaSeparatedValuesParser(char separator, boolean trimFields)
    {
        _separator = separator;
        _trimFields = trimFields;
        _determiner = null;
    }

    public CommaSeparatedValuesParser(SeparatorDeterminer determiner, boolean trimFields)
    {
        _separator = '\0';
        _trimFields = trimFields;
        _determiner = determiner;
    }

    public char getSeparator()
    {
        if (_determiner != null)
        {
            throw new IllegalStateException("Separator not determined yet");
        }
        return _separator;
    }

    public String[] parseLine(String line)
    {
        if (_determiner != null) {
            _separator = _determiner.determineSeparator(line);
            _determiner = null;
        }
        CharBuffer elem = CharBuffer.allocate(line.length());
        List<String> data = new ArrayList<String>();
        boolean quoted = false;
        boolean isQuote = false;
        for (char c : line.toCharArray()) {
            if (!quoted && c == _separator) {
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
            } else if (!_trimFields || !Character.isSpaceChar(c) || elem.position() > 0) {
                elem.append(c);
            }
        }
        if (quoted && !isQuote) {
            throw new IllegalArgumentException("Missing end of quoting: " + line);
        }
        data.add(getValue(elem));
        return data.toArray(new String[data.size()]);
    }

    private String getValue(CharBuffer value)
    {
        int length = value.position();
        value.rewind();
        value.limit(length);
        try {
            return value.toString();
        } finally {
            value.clear();
        }
    }

    String quote(String elem)
    {
        boolean containsQuote = elem.indexOf(QUOTE) != -1;
        boolean quote = elem.indexOf(_separator) != -1 || containsQuote;
        if (quote) {
            if (containsQuote) {
                elem = QUOTE_PATTERN.matcher(elem).replaceAll(QUOTE_REPLACEMENT);
            }
            elem = QUOTE + elem + QUOTE;
        }
        return elem;
    }

    public String generateLine(String[] line)
    {
        StringBuffer out = new StringBuffer();
        for (String elem : line) {
            if (out.length() > 0) {
                out.append(_separator);
            }
            out.append(quote(elem));
        }
        return out.toString();
    }
}
