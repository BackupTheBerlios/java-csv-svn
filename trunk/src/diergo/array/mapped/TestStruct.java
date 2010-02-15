package diergo.array.mapped;

class TestStruct
{
    public String _string;
    public int _int;
    public boolean _boolean;
    public Long _long;

    public TestStruct()
    {
        this(null, 0, false, null);
    }

    public TestStruct(String s, int i, boolean b, Long l)
    {
        _string = s;
        _int = i;
        _boolean = b;
        _long = l;
    }
}
