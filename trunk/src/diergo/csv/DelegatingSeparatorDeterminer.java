package diergo.csv;

/**
 * Determines the separator by delegating to a provider. This can be used to use
 * the same separator from one reader in other readers and writers.
 * 
 * @since 1.2
 */
public class DelegatingSeparatorDeterminer
    implements SeparatorDeterminer
{
  private final SeparatorProvider delegate;

  public DelegatingSeparatorDeterminer(SeparatorProvider delegate)
  {
    this.delegate = delegate;
  }

  public char determineSeparator(String line)
  {
    return delegate.getSeparator();
  }

  public static interface SeparatorProvider
  {
    public char getSeparator();
  }
}
