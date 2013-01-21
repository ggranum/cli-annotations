/*
 * Copyright 2012 Geoff M. Granum,All Rights Reserved.
 *
 * 11/3/12 6:25 PM 
 * @author ggranum
 */
package biz.granum.cli.plugintest;

import biz.granum.cli.configuration.BooleanConfigurations;
import biz.granum.cli.exception.CliUserInputException;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public abstract class UserInputErrorsTest<I> extends PluginTest<I> {

    @Test(expected = CliUserInputException.class)
    public void testUnknownArgument() throws Exception {
        I args = getTestData().getTestUnknownArgument();
        BooleanConfigurations model = getPopulatedModel(args, BooleanConfigurations.class);
        assertThat(model, nullValue());
    }

}
 
