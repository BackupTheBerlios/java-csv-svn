package diergo.csv;

/**
 * Uses a fixed separator for the fields of a CSV line.
 * @since 1.1
 */
public class FixedSeparatorDeterminer implements SeparatorDeterminer
{
    private final char _separator;

    public FixedSeparatorDeterminer(char separator)
    {
        _separator = separator;
    }

    /**
     * Returns the separator passed to the {@linkplain #FixedSeparatorDeterminer(char) constructor}.
     * The line parameter is ignored.
     */
    public char determineSeparator(String line)
    {
        return _separator;
    }
}
