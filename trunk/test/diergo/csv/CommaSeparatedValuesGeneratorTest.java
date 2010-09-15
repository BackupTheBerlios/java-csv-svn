package diergo.csv;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CommaSeparatedValuesGeneratorTest
{
  @Test
  public void valueWithSeparatorIsQuoted()
  {
    assertEquals("\"Hallo, Du\"", new CommaSeparatedValuesGenerator(new FixedSeparatorDeterminer(','))
        .transform(new String[] { "Hallo, Du" }));
  }

  @Test
  public void normalValueIsNotQuoted()
  {
    assertEquals("Hallo", new CommaSeparatedValuesGenerator(new FixedSeparatorDeterminer(','))
        .transform(new String[] { "Hallo" }));
  }

  @Test
  public void valueWithQuoteIsQuotedAndQuoteIsDoubled()
  {
    assertEquals("\"Hallo\"\" Du\"", new CommaSeparatedValuesGenerator(new FixedSeparatorDeterminer('"'))
        .transform(new String[] { "Hallo\" Du" }));
  }

  @Test
  public void lineIsWrittenQuoted()
  {
    assertEquals("Hallo,\"Wie geht,s\"", new CommaSeparatedValuesGenerator(new FixedSeparatorDeterminer(','))
        .transform(new String[] { "Hallo", "Wie geht,s" }));
  }
}
