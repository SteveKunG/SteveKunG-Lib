package com.stevekung.stevekunglib.utils;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

// Big thanks to https://github.com/google/gson/issues/1794#issuecomment-812964421 and https://gist.github.com/knightzmc/cf26d9931d32c78c5d777cc719658639
public class RecordTypeAdapterFactory implements TypeAdapterFactory
{
    private static final Map<Class<?>, Object> PRIMITIVE_DEFAULTS = Maps.newHashMap();

    static
    {
        PRIMITIVE_DEFAULTS.put(byte.class, (byte) 0);
        PRIMITIVE_DEFAULTS.put(int.class, 0);
        PRIMITIVE_DEFAULTS.put(long.class, 0L);
        PRIMITIVE_DEFAULTS.put(short.class, (short) 0);
        PRIMITIVE_DEFAULTS.put(double.class, 0D);
        PRIMITIVE_DEFAULTS.put(float.class, 0F);
        PRIMITIVE_DEFAULTS.put(char.class, '\0');
        PRIMITIVE_DEFAULTS.put(boolean.class, false);
    }

    private final Map<RecordComponent, List<String>> recordComponentNameCache = Maps.newConcurrentMap();

    @Override
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type)
    {
        var clazz = (Class<T>) type.getRawType();

        if (!clazz.isRecord())
        {
            return null;
        }

        var delegate = gson.getDelegateAdapter(this, type);

        return new TypeAdapter<>()
        {
            @Override
            public void write(JsonWriter out, T value) throws IOException
            {
                delegate.write(out, value);
            }

            @Override
            public T read(JsonReader reader) throws IOException
            {
                if (reader.peek() == JsonToken.NULL)
                {
                    reader.nextNull();
                    return null;
                }
                else
                {
                    var recordComponents = clazz.getRecordComponents();
                    var typeMap = Maps.<String, TypeToken<?>>newHashMap();

                    for (var recordComponent : recordComponents)
                    {
                        for (var name : RecordTypeAdapterFactory.this.getRecordComponentNames(recordComponent))
                        {
                            typeMap.put(name, TypeToken.get(recordComponent.getGenericType()));
                        }
                    }

                    var argsMap = Maps.<String, Object>newHashMap();
                    reader.beginObject();

                    while (reader.hasNext())
                    {
                        var name = reader.nextName();
                        var type = typeMap.get(name);

                        if (type != null)
                        {
                            argsMap.put(name, gson.getAdapter(type).read(reader));
                        }
                        else
                        {
                            gson.getAdapter(Object.class).read(reader);
                        }
                    }

                    reader.endObject();

                    var argTypes = new Class<?>[recordComponents.length];
                    var args = new Object[recordComponents.length];

                    for (var i = 0; i < recordComponents.length; i++)
                    {
                        argTypes[i] = recordComponents[i].getType();
                        var names = RecordTypeAdapterFactory.this.getRecordComponentNames(recordComponents[i]);
                        Object value = null;
                        TypeToken<?> type = null;

                        // Find the first matching type and value
                        for (var name : names)
                        {
                            value = argsMap.get(name);
                            type = typeMap.get(name);

                            if (value != null && type != null)
                            {
                                break;
                            }
                        }

                        if (value == null && (type != null && type.getRawType().isPrimitive()))
                        {
                            value = PRIMITIVE_DEFAULTS.get(type.getRawType());
                        }
                        args[i] = value;
                    }

                    Constructor<T> constructor;

                    try
                    {
                        constructor = clazz.getDeclaredConstructor(argTypes);
                        constructor.setAccessible(true);
                        return constructor.newInstance(args);
                    }
                    catch (NoSuchMethodException | InstantiationException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
                    {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
    }

    /**
     * Get all names of a record component
     * If annotated with {@link SerializedName} the list returned will be the primary name first, then any alternative names
     * Otherwise, the component name will be returned.
     */
    private List<String> getRecordComponentNames(final RecordComponent recordComponent)
    {
        var inCache = this.recordComponentNameCache.get(recordComponent);

        if (inCache != null)
        {
            return inCache;
        }

        var names = Lists.<String>newArrayList();
        // The @SerializedName is compiled to be part of the componentName() method
        // The use of a loop is also deliberate, getAnnotation seemed to return null if Gson's package was relocated
        SerializedName annotation = null;

        for (var annotation1 : recordComponent.getAccessor().getAnnotations())
        {
            if (annotation1.annotationType() == SerializedName.class)
            {
                annotation = (SerializedName) annotation1;
                break;
            }
        }

        if (annotation != null)
        {
            names.add(annotation.value());
            names.addAll(Arrays.asList(annotation.alternate()));
        }
        else
        {
            names.add(recordComponent.getName());
        }
        var namesList = List.copyOf(names);
        this.recordComponentNameCache.put(recordComponent, namesList);
        return namesList;
    }
}