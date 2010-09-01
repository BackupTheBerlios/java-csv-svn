package diergo.array.mapped;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * This value transformer uses reflection to get and set values. Public members,
 * getters and setters are supported for primitive types, strings and other
 * objects.
 */
public class ReflectionBeanTransformer<T>
    extends ReflectionValueTransformer<T>
{
  private static final String[] DEFAULT_WRITE_METHOD_PREFIXES = new String[] { "set" };

  private final String _fieldPrefix;
  private final String[] _writeMethodPrefixes;

  public ReflectionBeanTransformer(Class<T> clazz, String fieldPrefix, String[] readMethodPrefixes,
      String[] writeMethodPrefixes)
  {
    super(clazz, readMethodPrefixes);
    _fieldPrefix = fieldPrefix;
    _writeMethodPrefixes = writeMethodPrefixes;
  }

  public ReflectionBeanTransformer(Class<T> clazz, String fieldPrefix)
  {
    super(clazz);
    _fieldPrefix = fieldPrefix;
    _writeMethodPrefixes = DEFAULT_WRITE_METHOD_PREFIXES;
  }

  public ReflectionBeanTransformer(Class<T> clazz)
  {
    this(clazz, "");
  }

  /**
   * A new data object is created, members are set ignoring leading underscores,
   * setters are invoked if found. Values without matching member or setter are
   * ignored. Values mapped to other than primitives or strings are set at an
   * object when a constructor with a single string argument is found.
   */
  public T transform(Map<String, String> data)
  {
    try {
      T object = _clazz.newInstance();
      for (Field f : _clazz.getFields()) {
        if (isValid(f)) {
          String value = data.get(getName(f));
          if (value != null) {
            try {
              f.set(object, transformValue(value, f.getType()));
            } catch (IllegalAccessException e) {
              e.printStackTrace();
            }
          }
        }
      }
      for (Method m : _clazz.getMethods()) {
        if (isValid(m, true)) {
          String name = getName(m, _writeMethodPrefixes);
          String value = data.get(name);
          if (value != null) {
            try {
              m.invoke(object, transformValue(value, m.getParameterTypes()[0]));
            } catch (IllegalAccessException e) {
              e.printStackTrace();
            } catch (InvocationTargetException e) {
              throw transform(e);
            }
          }
        }
      }
      return object;
    } catch (InstantiationException e) {
      return null;
    } catch (IllegalAccessException e) {
      return null;
    }
  }

  @Override
  public Map<String, String> transform(T value)
  {
    Map<String, String> result = super.transform(value);
    for (Field f : value.getClass().getFields()) {
      if (isValid(f)) {
        result.put(getName(f), getValue(value, f));
      }
    }
    return result;
  }

  private boolean isValid(Field f)
  {
    return !Modifier.isStatic(f.getModifiers());
  }

  private String getName(Field f)
  {
    String name = f.getName();
    if (_fieldPrefix.length() > 0 && name.startsWith(_fieldPrefix)) {
      name = name.substring(_fieldPrefix.length());
    }
    return name;
  }

  private String getValue(T value, Field f)
  {
    try {
      return getStringValue(f.get(value));
    } catch (IllegalAccessException e) {
      return null;
    }
  }
}
