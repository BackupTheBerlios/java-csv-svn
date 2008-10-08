package diergo.array.mapped;

import java.lang.reflect.*;
import java.util.*;

/**
 * This value transformer uses reflection to get and set values. Public members,
 * getters and setters are supported for primitive types, strings and other
 * objects.
 */
public class ReflectionValueTransformer<T> implements
		ValueTransformer<T, String> {
	private static final String[] DEFAULT_READ_METHOD_PREFIXES =
		new String[] { "get", "has", "is" };
	private static final String[] DEFAULT_WRITE_METHOD_PREFIXES =
		new String[] { "set" };
	private static final Collection<String> FORBIDDEN_METHODS =
		Arrays.asList("getClass", "hashCode", "equals");

	private final Class<T> _clazz;
	private final String _fieldPrefix;
	private final String[] _readMethodPrefixes;
	private final String[] _writeMethodPrefixes;

	public ReflectionValueTransformer(Class<T> clazz, String fieldPrefix,
			String[] readMethodPrefixes, String[] writeMethodPrefixes) {
		_clazz = clazz;
		_fieldPrefix = fieldPrefix;
		_readMethodPrefixes = readMethodPrefixes;
		_writeMethodPrefixes = writeMethodPrefixes;
	}

	public ReflectionValueTransformer(Class<T> clazz, String fieldPrefix) {
		this(clazz, fieldPrefix, DEFAULT_READ_METHOD_PREFIXES,
				DEFAULT_WRITE_METHOD_PREFIXES);
	}

	public ReflectionValueTransformer(Class<T> clazz) {
		this(clazz, null);
	}

	/**
	 * A new data object is created, members are set ignoring leading
	 * underscores, setters are invoked if found. Values without matching member
	 * or setter are ignored. Values mapped to other than primitives or strings
	 * are set at an object when a constructor with a single string argument is
	 * found.
	 */
	public T transform(Map<String, String> data) {
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
					String name = getName(m, true);
					String value = data.get(name);
					if (value != null) {
						try {
							m.invoke(object, new Object[] { transformValue(
									value, m.getParameterTypes()[0]) });
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

	private Object transformValue(String value, Class<?> type) {
		if (type == Boolean.TYPE) {
			return Boolean.valueOf(value);
		} else if (type == Character.TYPE) {
			return new Character(value.charAt(0));
		} else if (type == Integer.TYPE) {
			return new Integer(value);
		} else if (type == Long.TYPE) {
			return new Long(value);
		} else if (type == Byte.TYPE) {
			return new Byte(value);
		} else if (type == Float.TYPE) {
			return new Float(value);
		} else if (type == Double.TYPE) {
			return new Double(value);
		} else if (type.isAssignableFrom(String.class)) {
			return value;
		} else {
			try {
				Constructor<?> c = type
						.getConstructor(new Class[] { String.class });
				return c.newInstance(new Object[] { value });
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * The data object is inspected and values of public members and getters are
	 * added to the result. Boolean getters starting is or has will be used,
	 * value types of unknown object types will result in a
	 * {@link Object#toString()} value.
	 */
	public Map<String, String> transform(T value) {
		Map<String, String> result = new LinkedHashMap<String, String>();
		for (Field f : value.getClass().getFields()) {
			if (isValid(f)) {
				result.put(getName(f), getValue(value, f));
			}
		}
		for (Method m : value.getClass().getMethods()) {
			if (isValid(m, false)) {
				String name = getName(m, false);
				if (name != null && !result.keySet().contains(name)) {
					result.put(name, getValue(value, m));
				}
			}
		}
		return result;
	}

	private boolean isValid(Method m, boolean write) {
		return !FORBIDDEN_METHODS.contains(m.getName())
				&& !Modifier.isStatic(m.getModifiers())
				&& m.getParameterTypes().length == (write ? 1 : 0);
	}

	private boolean isValid(Field f) {
		return !Modifier.isStatic(f.getModifiers());
	}

	private String getName(Field f) {
		String name = f.getName();
		if (_fieldPrefix != null && name.startsWith(_fieldPrefix)) {
			name = name.substring(_fieldPrefix.length());
		}
		return name;
	}

	private String getName(Method m, boolean write) {
		String name = m.getName();
		for (String prefix : write ? _writeMethodPrefixes : _readMethodPrefixes) {
			if (hasPrefix(name, prefix)) {
				StringBuffer result = new StringBuffer(name.substring(prefix
						.length()));
				result.setCharAt(0, Character.toLowerCase(result.charAt(0)));
				return result.toString();
			}
		}
		return null;
	}

	private boolean hasPrefix(String name, String prefix) {
		return name.startsWith(prefix) && name.length() > prefix.length()
				&& Character.isUpperCase(name.charAt(prefix.length()));
	}

	private String getValue(T value, Method m) {
		try {
			return getStringValue(m.invoke(value, new Object[0]));
		} catch (IllegalAccessException e) {
			return null;
		} catch (InvocationTargetException e) {
			throw transform(e);
		}
	}

	private RuntimeException transform(InvocationTargetException e) {
		if (e.getTargetException() instanceof RuntimeException) {
			return (RuntimeException) e.getTargetException();
		}
		return new IllegalStateException(e.getMessage());
	}

	private String getValue(T value, Field f) {
		try {
			return getStringValue(f.get(value));
		} catch (IllegalAccessException e) {
			return null;
		}
	}

	private String getStringValue(Object v) {
		if (v == null || v instanceof String) {
			return (String) v;
		}
		return v.toString();
	}
}
