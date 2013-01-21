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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public abstract class ModelFromPropertiesPropertyKeysTest {

    public abstract <T> CliModelProcessor<Properties, T> getProcessor(Class<T> model,
            String header,
            String footer);

    public <T> T getPopulatedModel(Properties args, Class<T> model) throws Exception {
        CliModelProcessor<Properties, T> processor = getProcessor(model, "", "");
        return processor.processArguments(args);
    }

    @Test
    public void testSetTrue_DefaultKey_WithLongOpt() throws Exception {
        Properties p = new Properties();
        p.put("BooleanConfigurations.fBooleanLongOption", "true");
        BooleanConfigurations model = getPopulatedModel(p, BooleanConfigurations.class);
        assertThat(model.showHelp(), is(false));
        assertThat(model.fBoolean, is(true));
        assertThat(model.dBooleanValues.isEmpty(), is(true));
    }

    @Test
    public void testSetFalse_DefaultKey_WithLongOpt() throws Exception {
        Properties p = new Properties();
        p.put("BooleanConfigurations.fBooleanLongOption", "false");
        BooleanConfigurations model = getPopulatedModel(p, BooleanConfigurations.class);
        assertThat(model.showHelp(), is(false));
        assertThat(model.fBoolean, is(false));
        assertThat(model.dBooleanValues.isEmpty(), is(true));
    }

    @Test
    public void testSetEmpty_DefaultKey_WithLongOpt() throws Exception {
        Properties p = new Properties();
        p.put("BooleanConfigurations.fBooleanLongOption", "");
        BooleanConfigurations model = getPopulatedModel(p, BooleanConfigurations.class);
        assertThat(model.showHelp(), is(false));
        assertThat(model.fBoolean, is(false));
        assertThat(model.dBooleanValues.isEmpty(), is(true));
    }

    @Test
    public void testSetTrue_DefaultKey_WithNoLongOpt() throws Exception {
        Properties p = new Properties();
        p.put("BooleanConfigurations.gBoolean", "true");
        BooleanConfigurations model = getPopulatedModel(p, BooleanConfigurations.class);
        assertThat(model.showHelp(), is(false));
        assertThat(model.fBoolean, is(false));
        assertThat(model.gBoolean, is(true));
        assertThat(model.dBooleanValues.isEmpty(), is(true));
    }

    @Test
    public void testSetTrue_CustomKey() throws Exception {
        Properties p = new Properties();
        p.put("custom.key.for.i.boolean", "true");
        BooleanConfigurations model = getPopulatedModel(p, BooleanConfigurations.class);
        assertThat(model.showHelp(), is(false));
        assertThat(model.fBoolean, is(false));
        assertThat(model.iBoolean, is(true));
        assertThat(model.dBooleanValues.isEmpty(), is(true));
    }
}

