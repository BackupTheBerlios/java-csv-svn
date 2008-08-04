package diergo.csv;

import java.util.HashMap;
import java.util.Map;

/**
 * A Utility to determine the separator of a CSV line.
 * @since 1.1
 */
public class SeparatorDeterminer
{
    public static final String DEFAULT_SEPARATORS = ",;\t";

    private final String _possibleSeparators;

    public SeparatorDeterminer(String possibleSeparators)
    {
        _possibleSeparators = possibleSeparators;
    }

    public SeparatorDeterminer()
    {
        this(DEFAULT_SEPARATORS);
    }

    public char determineSeparator(String line)
    {
        Map<Character, Integer> votes = new HashMap<Character, Integer>();
        for (char c : _possibleSeparators.toCharArray()) {
            try {
                votes.put(c, new CommaSeparatedValuesParser(c, false).parseLine(line).length);
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
        return separator;
    }
}
