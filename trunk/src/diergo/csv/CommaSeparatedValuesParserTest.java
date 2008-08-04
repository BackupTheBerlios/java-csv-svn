package diergo.csv;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CommaSeparatedValuesParserTest
{
    @Test
    public void valueWithSeparatorIsQuoted()
    {
        assertEquals("\"Hallo, Du\"", new CommaSeparatedValuesParser(',', true).quote("Hallo, Du"));
    }

    @Test
    public void normalValueIsNotQuoted()
    {
        assertEquals("Hallo", new CommaSeparatedValuesParser(',', true).quote("Hallo"));
    }

    @Test
    public void valueWithQuoteIsQuotedAndQuoteIsDoubled()
    {
        assertEquals("\"Hallo\"\" Du\"", new CommaSeparatedValuesParser('"', true).quote("Hallo\" Du"));
    }

    @Test
    public void lineIsWrittenQuoted()
    {
        assertEquals("Hallo,\"Wie geht,s\"", new CommaSeparatedValuesParser(',', true).generateLine(new String[] {"Hallo", "Wie geht,s"}));
    }
}
