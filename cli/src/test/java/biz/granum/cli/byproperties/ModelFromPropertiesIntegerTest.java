/*
 * Copyright 2012 Geoff M. Granum,All Rights Reserved.
 *
 * 11/3/12 6:25 PM 
 * @author ggranum
 */
package biz.granum.cli.byproperties;

import biz.granum.cli.CliModelProcessor;
import biz.granum.cli.configuration.IntegerConfigurations;
import java.util.Properties;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public abstract class ModelFromPropertiesIntegerTest {

    public abstract <T> CliModelProcessor<Properties, T> getProcessor(Class<T> model,
            String header,
            String footer);

    public <T> T getPopulatedModel(Properties p, Class<T> model) throws Exception {
        CliModelProcessor<Properties, T> processor = getProcessor(model, "", "");
        return processor.processInput(p);
    }

    @Test
    public void testSimpleDefaultIsZero() throws Exception {
        Properties p = new Properties();
        // add nothing, test default.
        IntegerConfigurations model = getPopulatedModel(p, IntegerConfigurations.class);
        assertThat(model.aIntValue, is(0));
    }

    @Test
    public void testSimpleValue_ShortOpt_Equals() throws Exception {
        Properties p = new Properties();
        p.put("IntegerConfigurations.aIntValue", "10");
        IntegerConfigurations model = getPopulatedModel(p, IntegerConfigurations.class);
        assertThat(model.aIntValue, is(10));
    }

    @Test
    public void testListDefaultValues() throws Exception {
        Properties p = new Properties();
        //        p.put("IntegerConfigurations.aIntValue", "10");
        IntegerConfigurations model = getPopulatedModel(p, IntegerConfigurations.class);
        assertThat(model.cIntValues.size(), is(3));
        assertThat(model.cIntValues.get(0), is(10));
        assertThat(model.cIntValues.get(1), is(20));
        assertThat(model.cIntValues.get(2), is(30));
    }

}

