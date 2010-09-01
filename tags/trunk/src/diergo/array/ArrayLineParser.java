package diergo.array;

/**
 * A line parse creates an array from a String.
 * 
 * @see ArrayLineReader
 */
public interface ArrayLineParser<E>
{
  public E[] parseLine(String line);
}
