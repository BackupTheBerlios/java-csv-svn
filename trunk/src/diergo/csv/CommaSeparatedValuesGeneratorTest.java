package diergo.csv;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CommaSeparatedValuesGeneratorTest
{
  @Test
  public void valueWithSeparatorIsQuoted()
  {
    assertEquals("\"Hallo, Du\"", new CommaSeparatedValuesGenerator(new FixedSeparatorDeterminer(','))
        .generateLine("Hallo, Du"));
  }

  @Test
  public void normalValueIsNotQuoted()
  {
    assertEquals("Hallo", new CommaSeparatedValuesGenerator(new FixedSeparatorDeterminer(',')).generateLine("Hallo"));
  }

  @Test
  public void valueWithQuoteIsQuotedAndQuoteIsDoubled()
  {
    assertEquals("\"Hallo\"\" Du\"", new CommaSeparatedValuesGenerator(new FixedSeparatorDeterminer('"'))
        .generateLine("Hallo\" Du"));
  }

  @Test
  public void lineIsWrittenQuoted()
  {
    assertEquals("Hallo,\"Wie geht,s\"", new CommaSeparatedValuesGenerator(new FixedSeparatorDeterminer(','))
        .generateLine("Hallo", "Wie geht,s"));
  }
}
