/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;
import java.util.logging.Logger;

/**
 *$Revision$
 * $Author$
 * $Date$
 * @author Rayan
 */
public final class PropertiesManager extends Properties
{
    private static final long serialVersionUID = -4599023842346938325L;
    private static Logger _log = Logger.getLogger(PropertiesManager.class.getName());
    private boolean _warn = true;

    /**
     *
     */
    public PropertiesManager()
    {
    }

    /**
     *
     * @param warn
     * @return this
     */
    public PropertiesManager setLog(boolean warn)
    {
        _warn = warn;

        return this;
    }

    // ===================================================================================
    /**
     *
     * @param name
     * @throws IOException
     */
    public PropertiesManager(String name) throws IOException
    {
        load(new FileInputStream(name));
    }

    /**
     *
     * @param file
     * @throws IOException
     */
    public PropertiesManager(File file) throws IOException
    {
        load(new FileInputStream(file));
    }

    /**
     *
     * @param inStream
     * @throws IOException
     */
    public PropertiesManager(InputStream inStream) throws IOException
    {
        load(inStream);
    }

    /**
     *
     * @param reader
     * @throws IOException
     */
    public PropertiesManager(Reader reader) throws IOException
    {
        load(reader);
    }

    // ===================================================================================
    /**
     *
     * @param name
     * @throws IOException
     */
    public void load(String name) throws IOException
    {
        load(new FileInputStream(name));
    }

    /**
     *
     * @param file
     * @throws IOException
     */
    public void load(File file) throws IOException
    {
        load(new FileInputStream(file));
    }

    @Override
    public void load(InputStream inStream) throws IOException
    {
        try
        {
            super.load(inStream);
        }
        finally
        {
            inStream.close();
        }
    }

    @Override
    public void load(Reader reader) throws IOException
    {
        try
        {
            super.load(reader);
        }
        finally
        {
            reader.close();
        }
    }

    // ===================================================================================
    @Override
    public String getProperty(String key)
    {
        String property = super.getProperty(key);

        if(property==null)
        {
            if(_warn)
            {
                _log.warning("L2Properties: Missing property for key - "+key);
            }

            return null;
        }

        return property.trim();
    }

    @Override
    public String getProperty(String key, String defaultValue)
    {
        String property = super.getProperty(key, defaultValue);

        if(property==null)
        {
            if(_warn)
            {
                _log.warning("L2Properties: Missing defaultValue for key - "+key);
            }

            return null;
        }

        return property.trim();
    }

    // ===================================================================================
    /**
     *
     * @param name
     * @return the Boolean
     */
    public boolean getBool(String name)
    {
        Object val = get(name);
        if(val==null)
        {
            throw new IllegalArgumentException("Boolean value required, but not specified");
        }
        if(val instanceof Boolean)
        {
            return (Boolean) val;
        }
        try
        {
            return Boolean.parseBoolean((String) val);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Boolean value required, but found: "+val);
        }
    }

    /**
     *
     * @param name
     * @param deflt
     * @return Boolean
     */
    public boolean getBool(String name, String deflt)
    {
        Object val = get(name);
        if(val==null)
        {
            val = deflt;
        }
        if(val instanceof Boolean)
        {
            return (Boolean) val;
        }
        try
        {
            return Boolean.parseBoolean((String) val);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Boolean value required, but found: "+val);
        }
    }

    /**
     *
     * @param name
     * @param deflt
     * @return the boolean
     */
    public boolean getBool(String name, boolean deflt)
    {
        Object val = get(name);
        if(val==null)
        {
            return deflt;
        }
        if(val instanceof Boolean)
        {
            return (Boolean) val;
        }
        try
        {
            return Boolean.parseBoolean((String) val);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Boolean value required, but found: "+val);
        }
    }

    /**
     *
     * @param name
     * @return Byte
     */
    public byte getByte(String name)
    {
        Object val = get(name);
        if(val==null)
        {
            throw new IllegalArgumentException("Byte value required, but not specified");
        }
        if(val instanceof Number)
        {
            return ((Number) val).byteValue();
        }
        try
        {
            return Byte.decode((String) val);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Byte value required, but found: "+val);
        }
    }

    /**
     *
     * @param name
     * @param deflt
     * @return Byte
     */
    public byte getByte(String name, byte deflt)
    {
        Object val = get(name);
        if(val==null)
        {
            return deflt;
        }
        if(val instanceof Number)
        {
            return ((Number) val).byteValue();
        }
        try
        {
            return Byte.decode((String) val);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Byte value required, but found: "+val);
        }
    }

    /**
     * 
     * @param name
     * @param deflt
     * @return Byte
     */
    public byte getByte(String name, String deflt)
    {
        Object val = get(name);
        if(val==null)
        {
            val = deflt;
        }
        if(val instanceof Number)
        {
            return ((Number) val).byteValue();
        }
        try
        {
            return Byte.decode((String) val);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Byte value required, but found: "+val);
        }
    }

    /**
     *
     * @param name
     * @return Short
     */
    public short getShort(String name)
    {
        Object val = get(name);
        if(val==null)
        {
            throw new IllegalArgumentException("Short value required, but not specified");
        }
        if(val instanceof Number)
        {
            return ((Number) val).shortValue();
        }
        try
        {
            return Short.decode((String) val);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Short value required, but found: "+val);
        }
    }

    /**
     *
     * @param name
     * @param deflt
     * @return Short
     */
    public short getShort(String name, short deflt)
    {
        Object val = get(name);
        if(val==null)
        {
            return deflt;
        }
        if(val instanceof Number)
        {
            return ((Number) val).shortValue();
        }
        try
        {
            return Short.decode((String) val);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Short value required, but found: "+val);
        }
    }

    /**
     *
     * @param name
     * @param deflt
     * @return Short
     */
    public short getShort(String name, String deflt)
    {
        Object val = get(name);
        if(val==null)
        {
            val = deflt;
        }
        if(val instanceof Number)
        {
            return ((Number) val).shortValue();
        }
        try
        {
            return Short.decode((String) val);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Short value required, but found: "+val);
        }
    }

    /**
     *
     * @param name
     * @return Integer
     */
    public int getInteger(String name)
    {
        Object val = get(name);
        if(val==null)
        {
            throw new IllegalArgumentException("Integer value required, but not specified");
        }
        if(val instanceof Number)
        {
            return ((Number) val).intValue();
        }
        try
        {
            return Integer.decode((String) val);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Integer value required, but found: "+val);
        }
    }

    /**
     *
     * @param name
     * @param deflt
     * @return Integer
     */
    public int getInteger(String name, int deflt)
    {
        Object val = get(name);
        if(val==null)
        {
            return deflt;
        }
        if(val instanceof Number)
        {
            return ((Number) val).intValue();
        }
        try
        {
            return Integer.decode((String) val);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Integer value required, but found: "+val);
        }
    }

    /**
     *
     * @param name
     * @param deflt
     * @return Integer
     */
    public int getInteger(String name, String deflt)
    {
        Object val = get(name);
        if(val==null)
        {
            val = deflt;
        }
        if(val instanceof Number)
        {
            return ((Number) val).intValue();
        }
        try
        {
            return Integer.decode((String) val);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Integer value required, but found: "+val);
        }
    }

    /**
     *
     * @param name
     * @return Long
     */
    public long getLong(String name)
    {
        Object val = get(name);
        if(val==null)
        {
            throw new IllegalArgumentException("Integer value required, but not specified");
        }
        if(val instanceof Number)
        {
            return ((Number) val).longValue();
        }
        try
        {
            return Long.decode((String) val);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Integer value required, but found: "+val);
        }
    }

    /**
     *
     * @param name
     * @param deflt
     * @return Long
     */
    public long getLong(String name, long deflt)
    {
        Object val = get(name);
        if(val==null)
        {
            return deflt;
        }
        if(val instanceof Number)
        {
            return ((Number) val).longValue();
        }
        try
        {
            return Long.decode((String) val);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Integer value required, but found: "+val);
        }
    }

    /**
     *
     * @param name
     * @param deflt
     * @return Long
     */
    public long getLong(String name, String deflt)
    {
        Object val = get(name);
        if(val==null)
        {
            val = deflt;
        }
        if(val instanceof Number)
        {
            return ((Number) val).longValue();
        }
        try
        {
            return Long.decode((String) val);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Integer value required, but found: "+val);
        }
    }

    /**
     *
     * @param name
     * @return Float
     */
    public float getFloat(String name)
    {
        Object val = get(name);
        if(val==null)
        {
            throw new IllegalArgumentException("Float value required, but not specified");
        }
        if(val instanceof Number)
        {
            return ((Number) val).floatValue();
        }
        try
        {
            return Float.parseFloat((String) val);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Float value required, but found: "+val);
        }
    }

    /**
     *
     * @param name
     * @param deflt
     * @return Float
     */
    public float getFloat(String name, float deflt)
    {
        Object val = get(name);
        if(val==null)
        {
            return deflt;
        }
        if(val instanceof Number)
        {
            return ((Number) val).floatValue();
        }
        try
        {
            return Float.parseFloat((String) val);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Float value required, but found: "+val);
        }
    }

    /**
     *
     * @param name
     * @param deflt
     * @return Float
     */
    public float getFloat(String name, String deflt)
    {
        Object val = get(name);
        if(val==null)
        {
            val = deflt;
        }
        if(val instanceof Number)
        {
            return ((Number) val).floatValue();
        }
        try
        {
            return Float.parseFloat((String) val);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Float value required, but found: "+val);
        }
    }

    /**
     *
     * @param name
     * @return Double
     */
    public double getDouble(String name)
    {
        Object val = get(name);
        if(val==null)
        {
            throw new IllegalArgumentException("Float value required, but not specified");
        }
        if(val instanceof Number)
        {
            return ((Number) val).doubleValue();
        }
        try
        {
            return Double.parseDouble((String) val);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Float value required, but found: "+val);
        }
    }

    /**
     *
     * @param name
     * @param deflt
     * @return Double
     */
    public double getDouble(String name, double deflt)
    {
        Object val = get(name);
        if(val==null)
        {
            return deflt;
        }
        if(val instanceof Number)
        {
            return ((Number) val).doubleValue();
        }
        try
        {
            return Double.parseDouble((String) val);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Float value required, but found: "+val);
        }
    }

    /**
     *
     * @param name
     * @param deflt
     * @return Double
     */
    public double getDouble(String name, String deflt)
    {
        Object val = get(name);
        if(val==null)
        {
            val = deflt;
        }
        if(val instanceof Number)
        {
            return ((Number) val).doubleValue();
        }
        try
        {
            return Double.parseDouble((String) val);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Float value required, but found: "+val);
        }
    }

    /**
     *
     * @param name
     * @return String
     */
    public String getString(String name)
    {
        Object val = get(name);
        if(val==null)
        {
            throw new IllegalArgumentException("String value required, but not specified");
        }
        return String.valueOf(val);
    }

    /**
     *
     * @param name
     * @param deflt
     * @return String
     */
    public String getString(String name, String deflt)
    {
        Object val = get(name);
        if(val==null)
        {
            return deflt;
        }
        return String.valueOf(val);
    }

    /**
     *
     * @param <T>
     * @param name
     * @param enumClass
     * @return Enum
     */
    @SuppressWarnings("unchecked")
    public <T extends Enum<T>>   T getEnum(String name, Class<T> enumClass)
    {
        Object val = get(name);
        if(val==null)
        {
            throw new IllegalArgumentException("Enum value of type "+enumClass.
                    getName()
                                               +" required, but not specified");
        }
        if(enumClass.isInstance(val))
        {
            return (T) val;
        }
        try
        {
            return Enum.valueOf(enumClass, String.valueOf(val));
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Enum value of type "+enumClass.
                    getName()+"required, but found: "
                                               +val);
        }
    }

    /**
     *
     * @param <T>
     * @param name
     * @param enumClass
     * @param deflt
     * @return Enum
     */
    @SuppressWarnings("unchecked")
    public <T extends Enum<T>>   T getEnum(String name, Class<T> enumClass, T deflt)
    {
        Object val = get(name);
        if(val==null)
        {
            return deflt;
        }
        if(enumClass.isInstance(val))
        {
            return (T) val;
        }
        try
        {
            return Enum.valueOf(enumClass, String.valueOf(val));
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Enum value of type "+enumClass.
                    getName()+"required, but found: "
                                               +val);
        }
    }

    /**
     *
     * @param <T>
     * @param name
     * @param enumClass
     * @param deflt
     * @return Enum
     */
    @SuppressWarnings("unchecked")
    public <T extends Enum<T>>   T getEnum(String name, Class<T> enumClass, String deflt)
    {
        Object val = get(name);
        if(val==null)
        {
            val = deflt;
        }
        if(enumClass.isInstance(val))
        {
            return (T) val;
        }
        try
        {
            return Enum.valueOf(enumClass, String.valueOf(val));
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Enum value of type "+enumClass.
                    getName()+"required, but found: "
                                               +val);
        }
    }
}
