package diergo.csv;

import static diergo.csv.Option.COMMENTED_HEADER;
import static diergo.csv.Option.COMMENTS_SKIPPED;
import static org.junit.Assert.assertArrayEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

public class CommaSeparatedValuesReaderTest
{
  @Test
  public void lineWithoutSeparatorResultsInSingleString()
      throws IOException
  {
    String[] data = new CommaSeparatedValuesReader(new StringReader("\n"), ';').read();
    assertArrayEquals(new String[0], data);
  }

  @Test
  public void separatedLineIsSplitted()
      throws IOException
  {
    String[] data = new CommaSeparatedValuesReader(new StringReader("a;b;c"), ';').read();
    assertArrayEquals(new String[] { "a", "b", "c" }, data);
  }

  @Test
  public void quotedFieldWithSeparatorIsNotSplitted()
      throws IOException
  {
    String[] data = new CommaSeparatedValuesReader(new StringReader("\"hi;ho\""), ';').read();
    assertArrayEquals(new String[] { "hi;ho" }, data);
  }

  @Test
  public void quotedFieldWithQuotesIsUnquoted()
      throws IOException
  {
    String[] data = new CommaSeparatedValuesReader(new StringReader("\"\"\"hi\"\"ho\"\"\""), ';').read();
    assertArrayEquals(new String[] { "\"hi\"ho\"" }, data);
  }

  @Test(expected = IOException.class)
  public void unquotedFieldWithQuotesIsIllegal()
      throws IOException
  {
    new CommaSeparatedValuesReader(new StringReader("hi\"ho"), ';').read();
  }

  @Test(expected = IOException.class)
  public void quotedFieldWithMissingEndQuoteIsIllegal()
      throws IOException
  {
    new CommaSeparatedValuesReader(new StringReader("\"hi;ho"), ';').read();
  }

  @Test
  public void commentsAreSkipped()
      throws IOException
  {
    String[] data = new CommaSeparatedValuesReader(new StringReader("#comment\na"), ';', COMMENTS_SKIPPED).read();
    assertArrayEquals(new String[] { "a" }, data);
  }

  @Test
  public void commentedHeaderIsNotSkipped()
      throws IOException
  {
    String[] data = new CommaSeparatedValuesReader(new StringReader("#h1;h2\n#comment\na"), ';', COMMENTS_SKIPPED, COMMENTED_HEADER).read();
    assertArrayEquals(new String[] { "h1", "h2" }, data);
  }

  @Test
  public void iteratorReturnsSameAsRead()
      throws IOException
  {
    BufferedReader in = new BufferedReader(new StringReader("a;b;c"), 6);
    CommaSeparatedValuesReader reader = new CommaSeparatedValuesReader(in, ';');
    in.mark(6);
    String[] dataRead = reader.read();
    in.reset();
    assertArrayEquals(dataRead, reader.iterator().next());
  }
}
