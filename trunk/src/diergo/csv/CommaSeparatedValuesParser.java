package diergo.csv;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * The real parsing methods.
 * @since 1.1
 */
public class CommaSeparatedValuesParser
{
    public static char QUOTE = '"';

    private static final Pattern QUOTE_PATTERN = Pattern.compile(String.valueOf(QUOTE));
    private static final String QUOTE_REPLACEMENT = new String(new char[] {QUOTE, QUOTE});

    public static String[] parseLine(String line, char separator, boolean trimFields)
        throws IOException
    {
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
                    throw new IOException("CSV need quoting when containing quote: " + line);
                }
            } else if (!trimFields || !Character.isSpaceChar(c) || elem.position() > 0) {
                elem.append(c);
            }
        }
        if (quoted && !isQuote) {
            throw new IOException("Missing end of quoting: " + line);
        }
        data.add(getValue(elem));
        return data.toArray(new String[data.size()]);
    }

    private static String getValue(CharBuffer value)
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

    public static String quote(String elem, char separator)
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

    public static String write(String[] line, char separator)
    {
        StringBuffer out = new StringBuffer();
        for (String elem : line) {
            if (out.length() > 0) {
                out.append(separator);
            }
            out.append(quote(elem, separator));
        }
        return out.toString();
    }
}
