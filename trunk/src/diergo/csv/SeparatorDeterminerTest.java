package diergo.csv;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SeparatorDeterminerTest
{
    @Test
    public void withoutSeparatorsTheFirstOneIsUsed()
    {
        assertEquals(',', new SeparatorDeterminer(",;").determineSeparator("Hallo"));
    }

    @Test
    public void withSeparatorsTheMostOccuringIsUsed()
    {
        assertEquals(';', new SeparatorDeterminer(",;").determineSeparator("Hallo, Du;Wie;Gehts"));
    }

    @Test
    public void quotedSeparatorsAreIgnored()
    {
        assertEquals(',', new SeparatorDeterminer(",;").determineSeparator("Hallo,\"Du;Wie;Gehts\""));
    }

    @Test
    public void onSameOccurenceCountTheFirstOneIsUsed()
    {
        assertEquals(',', new SeparatorDeterminer(",;").determineSeparator("Hallo;Du,Wie"));
    }
}
