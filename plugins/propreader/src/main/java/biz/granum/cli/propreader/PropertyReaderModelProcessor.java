/*
 * Copyright 2013 Geoff M. Granum,All Rights Reserved.
 *
 * 1/19/13 4:04 PM 
 * @author ggranum
 */
package biz.granum.cli.propreader;

import biz.granum.cli.CliModelProcessor;
import biz.granum.cli.exception.CliCouldNotProcessArgumentsException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertyReaderModelProcessor<T> extends CliModelProcessor<Properties, T> {

    private final List<InputStream> inputSources = new ArrayList<InputStream>();

    private final Properties input = new Properties();

    public PropertyReaderModelProcessor(Class<T> modelClass) {
        this(modelClass, "", "");
    }

    public PropertyReaderModelProcessor(Class<T> modelClass,
            String helpHeader, String helpFooter) {
        super(modelClass, new PropertyReaderConfigurationProvider(), helpHeader, helpFooter);
    }

    /**
     * Add an input source by resource path; it is safe to add paths that may not exist.
     * Input sources will be read in the order added, and accumulated in a single instance of Properties.
     * If the path doesn't exist this method will return null.
     * This method wraps the possible IOException in a RuntimeException and rethrows it.
     *
     * @param path
     *         A resource path, which may or may not point to an existing resource.
     * @param clazz
     *         The class the path is relative to.
     * @return The Properties object that was loaded from the provided stream, or null. This is NOT the sum of all
     *         added input sources.
     */
    public Properties addResourcePath(String path, Class clazz) throws RuntimeException {
        InputStream stream = clazz.getResourceAsStream(path);
        return this.addInputSource(stream);
    }

    /**
     * Add an input source directly; may be null.
     * Input sources will be read in the order added, and accumulated in a single instance of Properties.
     *
     * @param stream
     *         The InputStream; may be null.
     * @return The Properties object that was loaded from the provided stream, or null. This is NOT the sum of all
     *         added input sources.
     * @throws RuntimeException
     *         If <code>stream</code> is present but cannot be read as a properties file.
     */
    public Properties addInputSource(InputStream stream) throws RuntimeException {
        if(stream != null) {
            try {
                Properties tempProperties = new Properties();
                tempProperties.load(stream);
                input.putAll(tempProperties);
                inputSources.add(stream);
                return tempProperties;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public T processInput() throws CliCouldNotProcessArgumentsException, IllegalAccessException {
        return processInput(input);
    }

    @Override
    public T processInput(Properties arguments)
            throws CliCouldNotProcessArgumentsException, IllegalAccessException {
        return super.processInput(arguments);
    }
}
 
