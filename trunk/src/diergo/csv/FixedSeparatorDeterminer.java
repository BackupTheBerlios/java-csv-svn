package diergo.csv;

/**
 * Uses a fixed separator for the fields of a CSV line.
 */
public class FixedSeparatorDeterminer implements SeparatorDeterminer
{
    private final char _separator;

    public FixedSeparatorDeterminer(char separator)
    {
        _separator = separator;
    }

    public char determineSeparator(String line)
    {
        return _separator;
    }
}
