package diergo.csv;

import java.io.IOException;
import java.util.Map;

import diergo.array.ArrayWriter;

/**
 * Each command for the {@linkplain Main main} has to implement this interface.
 */
public interface Command
{
  /**
   * Process the command reading from {@code in} and writing to {@code out}.
   * Recognized options are defined by the implementing class.
   */
  public void process(Iterable<String[]> in, ArrayWriter<String> out, Map<String, String> options)
      throws IOException;
}
