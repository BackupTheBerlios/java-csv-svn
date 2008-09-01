package diergo.csv;

import java.util.HashMap;
import java.util.Map;

/**
 * Determines the separator for the fields of a CSV line by parsing the first line.
 */
public class AutoSeparatorDeterminer implements SeparatorDeterminer
{
    public static final String DEFAULT_SEPARATORS = ",;\t";

    private final String _possibleSeparators;

    private char _separator = '\0';

    public AutoSeparatorDeterminer(String possibleSeparators)
    {
        _possibleSeparators = possibleSeparators;
    }

    public AutoSeparatorDeterminer()
    {
        this(DEFAULT_SEPARATORS);
    }

    public char determineSeparator(String line)
    {
        if (_separator != '\0')
        {
            return _separator;
        }

        Map<Character, Integer> votes = new HashMap<Character, Integer>();
        for (char c : _possibleSeparators.toCharArray()) {
            try {
                final char separator = c;
                votes.put(separator, new CommaSeparatedValuesParser(new SeparatorDeterminer()
                {

                    public char determineSeparator(String line)
                    {
                        return separator;
                    }
                }, false).parseLine(line).length);
            } catch (IllegalArgumentException e) {
                votes.put(c, 0);
            }
        }
        int v = 0;
        char separator = _possibleSeparators.charAt(0);
        for (char c : _possibleSeparators.toCharArray()) {
            if (votes.get(c).intValue() > v) {
                separator = c;
                v = votes.get(c);
            }
        }
        _separator = separator;
        return separator;
    }
}
