/*
 * Copyright 2013 Geoff M. Granum,All Rights Reserved.
 *
 * 1/19/13 4:04 PM 
 * @author ggranum
 */
package biz.granum.cli.propreader;

import biz.granum.cli.CliModelProcessor;
import biz.granum.cli.exception.CliCouldNotProcessArgumentsException;
import java.util.Properties;

public class PropertyReaderModelProcessor<T> extends CliModelProcessor<Properties, T> {

    public PropertyReaderModelProcessor(Class<T> modelClass,
            String helpHeader, String helpFooter) {
        super(modelClass, new PropertyReaderConfigurationProvider(), helpHeader, helpFooter);
    }

    @Override
    public T processInput(Properties arguments) throws
            CliCouldNotProcessArgumentsException,
            IllegalAccessException {
        return super.processInput(arguments);
    }
}
 
