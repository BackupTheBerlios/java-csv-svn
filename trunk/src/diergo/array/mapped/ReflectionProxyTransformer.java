package diergo.array.mapped;

import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * This value transformer creates proxies using reflection to get values. Public
 * getters are supported for primitive types, strings and other objects.
 * 
 * @since 1.2
 */
public class ReflectionProxyTransformer<T>
    extends ReflectionValueTransformer<T>
{

  public ReflectionProxyTransformer(Class<T> clazz)
  {
    super(clazz);
  }

  public ReflectionProxyTransformer(Class<T> clazz, String[] readMethodPrefixes)
  {
    super(clazz, readMethodPrefixes);
  }

  @SuppressWarnings("unchecked")
  public T transform(Map<String, String> data)
  {
    return (T) Proxy.newProxyInstance(_clazz.getClassLoader(), new Class[] { _clazz }, new DataGetter(data));
  }

  private class DataGetter
      implements InvocationHandler
  {

    private final Map<String, String> _data;

    public DataGetter(Map<String, String> data)
    {
      _data = data;
    }

    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable
    {
      if (isValid(method, false)) {
        String name = getName(method, _readMethodPrefixes);
        String value = _data.get(name);
        return transformValue(value, method.getReturnType());
      }
      return method.invoke(_data, args);
    }
  }
}
