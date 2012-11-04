/*
 * Copyright 2012 Geoff M. Granum,All Rights Reserved.
 *
 * 11/3/12 6:25 PM 
 * @author ggranum
 */
package biz.granum.cli;

import biz.granum.cli.configuration.*;
import biz.granum.cli.exception.*;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public abstract class CliVariousUserInputErrorsTest {


    public abstract CliProviderPlugin getPlugin();

    public <T> T getPopulatedModel(String[] args, Class<T> model) {
        CliProviderPlugin plugin = getPlugin();
        CliModelProcessor<T> processor = CliModelProcessor.create(
                model,
                plugin,
                "",
                ""
        );
        return processor.processArguments(args);
    }

    @Test(expected = CliUserInputException.class)
    public void testUnknownArgument() throws Exception {
        String[] args = {
                "--aiksjdfkjasdf",
                "foo,bar,baz"
        };
        BooleanConfigurations model = getPopulatedModel(args, BooleanConfigurations.class);
        assertThat(model, nullValue());
    }


}
 
