package com.sm24soft.common.util.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import com.sm24soft.common.exception.PropertyException;
import com.sm24soft.common.exception.ResourceException;
import com.sm24soft.common.util.ResourceUtil;

/**
 * Global properties
 */
public abstract class AbstractGlobalProperties {
    /** Properties Charset */
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    
    /** Loaded properties */
    private Properties properties;

    /**
     * Constructor
     * 
     * @param filename
     *            Property file name from class path
     * @throws PropertyException 
     */
    protected AbstractGlobalProperties(String filename) throws PropertyException {
        this.properties = loadProperties(filename);
    }

    /**
     * Constructor
     * 
     * @param path
     *            Property file path
     * @throws PropertyException 
     */
    protected AbstractGlobalProperties(Path path) throws PropertyException {
        this.properties = loadProperties(path);
    }

	/**
	 * Constructor
	 * 
	 * @param properties
	 *            Property
	 * @throws PropertyException
	 */
    protected AbstractGlobalProperties(Properties properties) throws PropertyException {
        if (properties == null) {
            throw new PropertyException("Properties is null");
        }
        this.properties = properties;
    }

	/**
	 * Load properties
	 * 
	 * @param filename
	 *            Property file name from class path
	 * @return Loaded properties
	 * @throws PropertyException
	 */
    private static Properties loadProperties(String filename) throws PropertyException {
        try {
            return loadProperties(ResourceUtil.getResourcePath(filename));
        } catch (ResourceException ex) {
            throw new PropertyException(ex);
        }
    }

	/**
	 * Load properties
	 * 
	 * @param path
	 *            Property file path
	 * @return Loaded properties
	 * @throws PropertyException
	 */
    private static Properties loadProperties(Path path) throws PropertyException {
        if (path == null) {
            throw new PropertyException("Path is null");
        }
        Properties properties = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(path, UTF_8)) {
            properties.load(reader);
        } catch (IOException ex) {
            throw new PropertyException(ex);
        }
        return properties;
    }

	/**
	 * Read string value from properties
	 * 
	 * @param key
	 *            Property key
	 * @return String value If property key missing, throw exception
	 * @throws PropertyException
	 */
    protected String getString(String key) throws PropertyException {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new PropertyException("Property missing");
        }
        return value;
    }

	/**
	 * Read string value from properties
	 * 
	 * @param key
	 *            Property key
	 * @param def
	 *            Default property value
	 * @return String value If property key missing, return default value
	 */
    protected String getString(String key, String def) {
        String value = properties.getProperty(key, def);
        return value;
    }

	/**
	 * Read integer value from properties
	 * 
	 * @param key
	 *            Property key
	 * @return Converted integer value If property key missing, throw exception
	 * @throws PropertyException 
	 */
    protected Integer getInteger(String key) throws PropertyException {
        Integer value = null;
        try {
            value = Integer.valueOf(getString(key));
        } catch (NumberFormatException ex) {
            throw new PropertyException(ex);
        }
        return value;
    }

	/**
	 * Read integer value from properties
	 * 
	 * @param key
	 *            Property key
	 * @param def
	 *            Default property value
	 * @return Converted integer value. If property key missing, return default
	 *         value
	 * @throws PropertyException 
	 */
    protected Integer getInteger(String key, Integer def) throws PropertyException {
        Integer value = null;
        String strValue = getString(key, null);
        try {
            value = (strValue == null) ? def : Integer.valueOf(strValue);
        } catch (NumberFormatException ex) {
            throw new PropertyException(ex);
        }
        return value;
    }

}
