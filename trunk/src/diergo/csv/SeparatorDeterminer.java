package diergo.csv;

/**
 * Determines the separator for the fields of a CSV line.
 */
public interface SeparatorDeterminer
{
    char determineSeparator(String line);
}
