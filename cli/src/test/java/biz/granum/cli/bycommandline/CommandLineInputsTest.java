/*
 * Copyright 2013 Geoff M. Granum,All Rights Reserved.
 *
 * 1/20/13 5:46 PM 
 * @author ggranum
 */
package biz.granum.cli.bycommandline;

import biz.granum.cli.configuration.BooleanConfigurations;
import biz.granum.cli.plugintest.PluginTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public abstract class CommandLineInputsTest extends PluginTest<String[]> {

    /**
     * Enabling a flag should always provide the default value.
     */
    @Test
    public void testLongOption() throws Exception {
        String[] args = {
                "-bBoolean"
        };
        BooleanConfigurations model = getPopulatedModel(args, BooleanConfigurations.class);
        assertThat(model.showHelp(), is(false));
        assertThat(model.booleanFlag, is(true));
        assertThat(model.listOfBooleans.isEmpty(), is(true));
    }
}
 
