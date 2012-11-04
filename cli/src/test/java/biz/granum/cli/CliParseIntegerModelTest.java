/*
 * Copyright 2012 Geoff M. Granum,All Rights Reserved.
 *
 * 11/3/12 6:25 PM 
 * @author ggranum
 */
package biz.granum.cli;

import biz.granum.cli.configuration.*;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public abstract class CliParseIntegerModelTest {


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

    @Test
    public void testSimpleDefaultIsZero() throws Exception {
        String[] args = {
        };
        IntegerConfigurations model = getPopulatedModel(args, IntegerConfigurations.class);
        assertThat(model.aIntValue, is(0));
    }

    @Test
    public void testSimpleValue_ShortOpt_Equals() throws Exception {
        String[] args = {
                "-a=10"
        };
        IntegerConfigurations model = getPopulatedModel(args, IntegerConfigurations.class);
        assertThat(model.aIntValue, is(10));
    }

    @Test
    public void testSimpleValue_LongOpt_Equals() throws Exception {
        String[] args = {
                "-aIntValue=10"
        };
        IntegerConfigurations model = getPopulatedModel(args, IntegerConfigurations.class);
        assertThat(model.aIntValue, is(10));
    }

    @Test
    public void testListDefaultValues() throws Exception {
        String[] args = {};
        IntegerConfigurations model = getPopulatedModel(args, IntegerConfigurations.class);
        assertThat(model.cIntValues.size(), is(3));
        assertThat(model.cIntValues.get(0), is(10));
        assertThat(model.cIntValues.get(1), is(20));
        assertThat(model.cIntValues.get(2), is(30));
    }


}
 
