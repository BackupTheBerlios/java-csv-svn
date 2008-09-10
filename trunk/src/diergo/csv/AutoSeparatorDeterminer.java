package diergo.csv;

import java.util.HashMap;
import java.util.Map;

/**
 * Determines the separator for the fields of a CSV line by parsing the first line.
 * @since 1.1
 */
public class AutoSeparatorDeterminer implements SeparatorDeterminer
{
    public static final String DEFAULT_SEPARATORS = ",;\t";

    private final String _possibleSeparators;

    private char _separator = '\0';

    public AutoSeparatorDeterminer(String possibleSeparators)
    {
    	if (possibleSeparators == null || possibleSeparators.length() == 0) {
    		throw new IllegalArgumentException("Possible separators must not be empty");
    	}
        _possibleSeparators = possibleSeparators;
    }

    /**
     * Creates a separator determiner using the {@link #DEFAULT_SEPARATORS}.
     */
    public AutoSeparatorDeterminer()
    {
        this(DEFAULT_SEPARATORS);
    }

    /**
     * Determines the separator from the first non empty line passed.
     * @throws IllegalStateException if the line is empty and no separator has been determined before
     */
    public char determineSeparator(String line)
    {
        if (_separator != '\0') {
            return _separator;
        }
        if (line == null || line.trim().length() == 0) {
        	throw new IllegalStateException("Separator not determined");
        }
        _separator = getBestVotedSeparator(voteForSeparators(line));
        return _separator;
    }

	private Map<Character, Integer> voteForSeparators(String line)
	{
		Map<Character, Integer> votes = new HashMap<Character, Integer>();
        for (char c : _possibleSeparators.toCharArray()) {
            try {
                final char separator = c;
                votes.put(separator, getFieldCountFromLineParsed(line, separator));
            } catch (IllegalArgumentException e) {
                votes.put(c, 0);
            }
        }
		return votes;
	}

	private char getBestVotedSeparator(Map<Character, Integer> votes)
	{
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

	private int getFieldCountFromLineParsed(String line, final char separator)
	{
		return new CommaSeparatedValuesParser(new SeparatorDeterminer()
		{

		    public char determineSeparator(String line)
		    {
		        return separator;
		    }
		}, false).parseLine(line).length;
	}
}
