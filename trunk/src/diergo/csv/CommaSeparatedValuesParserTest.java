package diergo.csv;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CommaSeparatedValuesParserTest
{
    @Test
    public void valueWithSeparatorIsQuoted()
    {
        assertEquals("\"Hallo, Du\"", new CommaSeparatedValuesParser(new FixedSeparatorDeterminer(','), true).generateLine("Hallo, Du"));
    }

    @Test
    public void normalValueIsNotQuoted()
    {
        assertEquals("Hallo", new CommaSeparatedValuesParser(new FixedSeparatorDeterminer(','), true).generateLine("Hallo"));
    }

    @Test
    public void valueWithQuoteIsQuotedAndQuoteIsDoubled()
    {
        assertEquals("\"Hallo\"\" Du\"", new CommaSeparatedValuesParser(new FixedSeparatorDeterminer('"'), true).generateLine("Hallo\" Du"));
    }

    @Test
    public void lineIsWrittenQuoted()
    {
        assertEquals("Hallo,\"Wie geht,s\"", new CommaSeparatedValuesParser(new FixedSeparatorDeterminer(','), true).generateLine("Hallo", "Wie geht,s"));
    }
}
