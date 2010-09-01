package diergo.array.mapped;

class TestData
    implements TestInterface
{
  private String _string;
  private int _int;
  private boolean _boolean;
  private Long _long;

  public TestData()
  {
    this(null, 0, false, null);
  }

  public TestData(String s, int i, boolean b, Long l)
  {
    _string = s;
    _int = i;
    _boolean = b;
    _long = l;
  }

  public boolean isBoolean()
  {
    return _boolean;
  }

  public void setBoolean(boolean b)
  {
    _boolean = b;
  }

  public int getInt()
  {
    return _int;
  }

  public void setInt(int i)
  {
    _int = i;
  }

  public String getString()
  {
    return _string;
  }

  public void setString(String string)
  {
    _string = string;
  }

  public Long getLong()
  {
    return _long;
  }

  public void setLong(Long l)
  {
    _long = l;
  }
}
