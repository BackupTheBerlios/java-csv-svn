package diergo.csv;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AutoSeparatorDeterminerTest
{
  @Test
  public void withoutSeparatorsTheFirstOneIsUsed()
  {
    assertEquals(',', new AutoSeparatorDeterminer(",;").determineSeparator("Hallo"));
  }

  @Test
  public void withSeparatorsTheMostOccuringIsUsed()
  {
    assertEquals(';', new AutoSeparatorDeterminer(",;").determineSeparator("Hallo, Du;Wie;Gehts"));
  }

  @Test
  public void quotedSeparatorsAreIgnored()
  {
    assertEquals(',', new AutoSeparatorDeterminer(",;").determineSeparator("Hallo,\"Du;Wie;Gehts\""));
  }

  @Test
  public void onSameOccurenceCountTheFirstOneIsUsed()
  {
    assertEquals(',', new AutoSeparatorDeterminer(",;").determineSeparator("Hallo;Du,Wie"));
  }

  @Test(expected = IllegalStateException.class)
  public void noLineResultsInException()
  {
    new AutoSeparatorDeterminer(",;").determineSeparator("");
  }
}
