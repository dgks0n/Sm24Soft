package com.sm24soft.common.util;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang.StringUtils;

import com.sm24soft.common.exception.ResourceException;

/**
 * Resource utilities class This class provide same resource using functions
 */
public final class ResourceUtil {
	
    /**
     * Private constructor
     */
    private ResourceUtil() {
    	
    }

    /**
     * Get resource path from class path
     * 
     * @param filename
     *            Resource file name
     * @return Resource path
     * @throws ResourceException
     *             Resource not found, or convert Path failed
     */
    public static Path getResourcePath(String filename) throws ResourceException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return getResourcePath(loader, filename);
    }

    /**
     * Get resource path from class path
     * 
     * @param classLoader
     *            Search base class loader
     * @param filename
     *            Resource file name
     * @return Resource path
     * @throws ResourceException
     *             Resource not found, or convert Path failed
     */
    public static Path getResourcePath(ClassLoader classLoader, String filename) throws ResourceException {
		try {
			if (classLoader == null) {
				throw new ResourceException("Classloader is null");
			}
			if (StringUtils.isEmpty(filename)) {
				throw new ResourceException("File name is null or empty");
			}
			URL url = classLoader.getResource(filename);
			if (url == null) {
				throw new ResourceException("File not found");
			}
			return Paths.get(url.toURI());
		} catch (URISyntaxException ex) {
			throw new ResourceException(filename, ex);
		}
    }
}
