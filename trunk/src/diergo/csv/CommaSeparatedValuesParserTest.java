package diergo.csv;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CommaSeparatedValuesParserTest
{
    @Test
    public void valueWithSeparatorIsQuoted()
    {
        assertEquals("\"Hallo, Du\"", CommaSeparatedValuesParser.quote("Hallo, Du", ','));
    }

    @Test
    public void normalValueIsNotQuoted()
    {
        assertEquals("Hallo", CommaSeparatedValuesParser.quote("Hallo", ','));
    }

    @Test
    public void valueWithQuoteIsQuotedAndQuoteIsDoubled()
    {
        assertEquals("\"Hallo\"\" Du\"", CommaSeparatedValuesParser.quote("Hallo\" Du", '"'));
    }

    @Test
    public void lineIsWrittenQuoted()
    {
        assertEquals("Hallo,\"Wie geht,s\"", CommaSeparatedValuesParser.write(new String[] {"Hallo", "Wie geht,s"}, ','));
    }
}
