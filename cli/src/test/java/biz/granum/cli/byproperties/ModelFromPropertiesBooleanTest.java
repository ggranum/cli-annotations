/*
 * Copyright 2012 Geoff M. Granum,All Rights Reserved.
 *
 * 11/3/12 6:25 PM 
 * @author ggranum
 */
package biz.granum.cli.byproperties;

import biz.granum.cli.CliModelProcessor;
import biz.granum.cli.configuration.BooleanConfigurations;
import java.util.Properties;
import org.junit.Test;

public abstract class ModelFromPropertiesBooleanTest {

    public abstract <T> CliModelProcessor<Properties, T> getProcessor(Class<T> model,
            String header,
            String footer);

    public <T> T getPopulatedModel(Properties args, Class<T> model) throws Exception {
        CliModelProcessor<Properties, T> processor = getProcessor(model, "", "");
        return processor.processInput(args);
    }

    @Test(expected = ClassCastException.class)
    public void testFailsForNonStringValuesInProperties() throws Exception {
        Properties p = new Properties();
        p.put("BooleanConfigurations.fBooleanLongOption", Boolean.FALSE);
        BooleanConfigurations model = getPopulatedModel(p, BooleanConfigurations.class);
    }
}

