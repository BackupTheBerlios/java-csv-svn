package diergo.array;

/**
 * A line generator creates a string from an array.
 * 
 * @see ArrayLineWriter
 */
public interface ArrayLineGenerator<E>
{
  public String generateLine(E... values);
}
