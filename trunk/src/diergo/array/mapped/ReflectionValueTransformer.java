package diergo.array.mapped;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This value transformer uses reflection transform values.
 */
public abstract class ReflectionValueTransformer<T>
    implements ValueTransformer<T, String>
{
  private static final String[] DEFAULT_READ_METHOD_PREFIXES = new String[] { "get", "has", "is" };
  private static final Collection<String> FORBIDDEN_METHODS = Arrays.asList("getClass", "hashCode", "equals");
  protected final Class<T> _clazz;
  protected final String[] _readMethodPrefixes;

  protected ReflectionValueTransformer(Class<T> clazz, String[] readMethodPrefixes)
  {
    _clazz = clazz;
    _readMethodPrefixes = readMethodPrefixes;
  }

  protected ReflectionValueTransformer(Class<T> clazz)
  {
    this(clazz, DEFAULT_READ_METHOD_PREFIXES);
  }

  protected Object transformValue(String value, Class<?> type)
  {
    boolean empty = value.length() == 0;
    if (type == Boolean.TYPE) {
      return empty ? false : Boolean.valueOf(value);
    } else if (type == Character.TYPE) {
      return empty ? '\0' : value.charAt(0);
    } else if (type == Integer.TYPE) {
      return empty ? 0 : new Integer(value);
    } else if (type == Long.TYPE) {
      return empty ? 0L : new Long(value);
    } else if (type == Short.TYPE) {
      return empty ? (short)0 : new Short(value);
    } else if (type == Byte.TYPE) {
      return empty ? (byte)0 : new Byte(value);
    } else if (type == Float.TYPE) {
      return empty ? 0.0f : new Float(value);
    } else if (type == Double.TYPE) {
      return empty ? 0.0 : new Double(value);
    } else if (type == String.class) {
      return value;
    } else {
      try {
        Constructor<?> c = type.getConstructor(new Class[] { String.class });
        return c.newInstance(value);
      } catch (Exception e) {
        return null;
      }
    }
  }

  /**
   * The data object is inspected and values of public members and getters are
   * added to the result. Boolean getters starting is or has will be used, value
   * types of unknown object types will result in a {@link Object#toString()}
   * value.
   */
  public Map<String, String> transform(T value)
  {
    Map<String, String> result = new LinkedHashMap<String, String>();
    for (Method m : value.getClass().getMethods()) {
      if (isValid(m, false)) {
        String name = getName(m, _readMethodPrefixes);
        if (name != null && !result.keySet().contains(name)) {
          result.put(name, getValue(value, m));
        }
      }
    }
    return result;
  }

  protected boolean isValid(Method m, boolean write)
  {
    return !FORBIDDEN_METHODS.contains(m.getName()) && !Modifier.isStatic(m.getModifiers())
        && m.getParameterTypes().length == (write ? 1 : 0);
  }

  protected String getName(Method m, String[] prefixes)
  {
    String name = m.getName();
    for (String prefix : prefixes) {
      if (hasPrefix(name, prefix)) {
        StringBuffer result = new StringBuffer(name.substring(prefix.length()));
        result.setCharAt(0, Character.toLowerCase(result.charAt(0)));
        return result.toString();
      }
    }
    return null;
  }

  private boolean hasPrefix(String name, String prefix)
  {
    return name.startsWith(prefix) && name.length() > prefix.length()
        && Character.isUpperCase(name.charAt(prefix.length()));
  }

  private String getValue(T value, Method m)
  {
    try {
      return getStringValue(m.invoke(value));
    } catch (IllegalAccessException e) {
      return null;
    } catch (InvocationTargetException e) {
      throw transform(e);
    }
  }

  protected RuntimeException transform(InvocationTargetException e)
  {
    if (e.getTargetException() instanceof RuntimeException) {
      return (RuntimeException) e.getTargetException();
    }
    return new IllegalStateException(e.getMessage());
  }

  protected String getStringValue(Object v)
  {
    if (v == null || v instanceof String) {
      return (String) v;
    }
    return v.toString();
  }
}
