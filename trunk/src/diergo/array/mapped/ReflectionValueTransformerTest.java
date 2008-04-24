package diergo.array.mapped;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ReflectionValueTransformerTest
{
    @Test
    public void propertiesAreRead()
    {
        Map<String, String> data = new ReflectionValueTransformer<PropertyObject>(PropertyObject.class)
                .transform(new PropertyObject("Hallo", 5, true, new Long(7L)));
        assertEquals("Hallo", data.get("string"));
        assertEquals("5", data.get("int"));
        assertEquals("true", data.get("boolean"));
        assertEquals("7", data.get("long"));
    }

    @Test
    public void propertiesAreWritten()
    {
        Map<String, String> data = new HashMap<String, String>();
        data.put("string", "Hallo");
        data.put("int", "5");
        data.put("boolean", "true");
        data.put("long", "7");
        PropertyObject object = new ReflectionValueTransformer<PropertyObject>(PropertyObject.class).transform(data);
        assertEquals("Hallo", object._string);
        assertEquals(5, object._int);
        assertEquals(true, object._boolean);
        assertEquals(new Long(7), object._long);
    }

    @Test
    public void methodsAreRead()
    {
        Map<String, String> data = new ReflectionValueTransformer<MethodObject>(MethodObject.class)
                .transform(new MethodObject("Hallo", 5, true, new Long(7L)));
        assertEquals("Hallo", data.get("string"));
        assertEquals("5", data.get("int"));
        assertEquals("true", data.get("boolean"));
        assertEquals("7", data.get("long"));
    }

    @Test
    public void methodsAreWritten()
    {
        Map<String, String> data = new HashMap<String, String>();
        data.put("string", "Hallo");
        data.put("int", "5");
        data.put("boolean", "true");
        data.put("long", "7");
        MethodObject object = new ReflectionValueTransformer<MethodObject>(MethodObject.class).transform(data);
        assertEquals("Hallo", object._string);
        assertEquals(5, object._int);
        assertEquals(true, object._boolean);
        assertEquals(new Long(7), object._long);
    }

    public static class PropertyObject
    {
        public String _string;
        public int _int;
        public boolean _boolean;
        public Long _long;

        public PropertyObject()
        {
            this(null, 0, false, null);
        }

        public PropertyObject(String s, int i, boolean b, Long l)
        {
            _string = s;
            _int = i;
            _boolean = b;
            _long = l;
        }

    }

    public static class MethodObject
    {
        private String _string;
        private int _int;
        private boolean _boolean;
        private Long _long;

        public MethodObject()
        {
            this(null, 0, false, null);
        }

        public MethodObject(String s, int i, boolean b, Long l)
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
}
