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
    private static final String[] EMPTY_LINE = new String[0];

	public static char QUOTE = '"';

    private static final Pattern QUOTE_PATTERN = Pattern.compile(String.valueOf(QUOTE));
    private static final String QUOTE_REPLACEMENT = new String(new char[] {QUOTE, QUOTE});

    private final SeparatorDeterminer _determiner;
    private final boolean _trimFields;

    public CommaSeparatedValuesParser(SeparatorDeterminer determiner, boolean trimFields)
    {
        _trimFields = trimFields;
        _determiner = determiner;
    }

    public String[] parseLine(String line)
    {
    	if (isEmpty(line)) {
    		return EMPTY_LINE;
    	}
        char separator = _determiner.determineSeparator(line);
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

	protected boolean isEmpty(String line) {
		return line == null || line.trim().length() == 0;
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
