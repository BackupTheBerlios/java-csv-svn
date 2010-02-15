package diergo.array.mapped;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ReflectionBeanTransformerTest
{
    @Test
    public void propertiesAreRead()
    {
        Map<String, String> data = new ReflectionBeanTransformer<TestStruct>(TestStruct.class, "_")
                .transform(new TestStruct("Hallo", 5, true, 7L));
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
        TestStruct object = new ReflectionBeanTransformer<TestStruct>(TestStruct.class, "_").transform(data);
        assertEquals("Hallo", object._string);
        assertEquals(5, object._int);
        assertEquals(true, object._boolean);
        assertEquals(new Long(7), object._long);
    }

    @Test
    public void methodsAreRead()
    {
        Map<String, String> data = new ReflectionBeanTransformer<TestData>(TestData.class)
                .transform(new TestData("Hallo", 5, true, 7L));
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
        TestData object = new ReflectionBeanTransformer<TestData>(TestData.class).transform(data);
        assertEquals("Hallo", object.getString());
        assertEquals(5, object.getInt());
        assertEquals(true, object.isBoolean());
        assertEquals(new Long(7), object.getLong());
    }
}
