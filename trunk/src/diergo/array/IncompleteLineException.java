package diergo.array;

/**
 * Thrown to indicate an incomplete line passed to a parser of an
 * {@link ArrayLineReader}.
 *
 * @since 2.1
 */
public class IncompleteLineException
    extends RuntimeException
{
  private static final long serialVersionUID = 141787859245031000L;
  private final String line;

  public IncompleteLineException(String line)
  {
    this.line = line;
  }

  public String getLine()
  {
    return line;
  }
}
