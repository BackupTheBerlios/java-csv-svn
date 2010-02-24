package diergo.array.mapped;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ReflectionProxyTransformerTest
{

  @Test
  public void methodsAreRead()
  {
    Map<String, String> data = new ReflectionProxyTransformer<TestInterface>(TestInterface.class)
        .transform(new TestData("Hallo", 5, true, 7L));
    assertEquals("Hallo", data.get("string"));
    assertEquals("5", data.get("int"));
    assertEquals("true", data.get("boolean"));
    assertEquals("7", data.get("long"));
  }

  @Test
  public void proxyReturnsData()
  {
    TestInterface object = new ReflectionProxyTransformer<TestInterface>(TestInterface.class).transform(createData());
    assertEquals("Hallo", object.getString());
    assertEquals(5, object.getInt());
    assertEquals(true, object.isBoolean());
    assertEquals(new Long(7), object.getLong());
  }

  // @Test
  public void proxyHasSameString()
  {
    HashMap<String, String> data = createData();
    TestInterface object = new ReflectionProxyTransformer<TestInterface>(TestInterface.class).transform(data);
    assertEquals(data.toString(), object.toString());
  }

  @Test
  public void proxyHasSameHashCode()
  {
    HashMap<String, String> data = createData();
    TestInterface object = new ReflectionProxyTransformer<TestInterface>(TestInterface.class).transform(data);
    assertEquals(data.hashCode(), object.hashCode());
  }

  @Test
  public void proxyEqualsData()
  {
    HashMap<String, String> data = createData();
    TestInterface object = new ReflectionProxyTransformer<TestInterface>(TestInterface.class).transform(data);
    assertTrue(object.equals(data));
  }

  private HashMap<String, String> createData()
  {
    HashMap<String, String> data = new HashMap<String, String>();
    data.put("string", "Hallo");
    data.put("int", "5");
    data.put("boolean", "true");
    data.put("long", "7");
    return data;
  }
}
