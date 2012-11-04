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

public abstract class CliParseBooleanModelTest {


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
    public void testShowHelp() throws Exception {
        String[] args = {
                "-h"
        };
        BooleanConfigurations model = getPopulatedModel(args, BooleanConfigurations.class);
        assertThat(model.showHelp(), is(true));
        assertThat(model.bBoolean, is(false));
        assertThat(model.dBooleanValues.isEmpty(), is(true));
    }

    @Test
    public void testTrue_NoArgument_Flag() throws Exception {
        String[] args = {
                "-b"
        };
        BooleanConfigurations model = getPopulatedModel(args, BooleanConfigurations.class);
        assertThat(model.showHelp(), is(false));
        assertThat(model.bBoolean, is(true));
        assertThat(model.dBooleanValues.isEmpty(), is(true));
    }

    @Test
    public void testTrue_NoArgument_Flag_LongOption() throws Exception {
        String[] args = {
                "-bBoolean"
        };
        BooleanConfigurations model = getPopulatedModel(args, BooleanConfigurations.class);
        assertThat(model.showHelp(), is(false));
        assertThat(model.bBoolean, is(true));
        assertThat(model.dBooleanValues.isEmpty(), is(true));
    }

    @Test
    public void testSetTrue_Argument_Equals() throws Exception {
        String[] args = {
                "-c=true"
        };
        BooleanConfigurations model = getPopulatedModel(args, BooleanConfigurations.class);
        assertThat(model.showHelp(), is(false));
        assertThat(model.cBoolean, is(true));
        assertThat(model.dBooleanValues.isEmpty(), is(true));
    }

    @Test
    public void testSetTrue_Argument_Space() throws Exception {
        String[] args = {
                "-c",
                "true"
        };
        BooleanConfigurations model = getPopulatedModel(args, BooleanConfigurations.class);
        assertThat(model.showHelp(), is(false));
        assertThat(model.cBoolean, is(true));
        assertThat(model.dBooleanValues.isEmpty(), is(true));
    }

    @Test
    public void testSetFalse_Argument_Equals() throws Exception {
        String[] args = {
                "-c=false"
        };
        BooleanConfigurations model = getPopulatedModel(args, BooleanConfigurations.class);
        assertThat(model.showHelp(), is(false));
        assertThat(model.cBoolean, is(false));
        assertThat(model.dBooleanValues.isEmpty(), is(true));
    }

    @Test
    public void testSetFalse_Argument_Space() throws Exception {
        String[] args = {
                "-c",
                "false"
        };
        BooleanConfigurations model = getPopulatedModel(args, BooleanConfigurations.class);
        assertThat(model.showHelp(), is(false));
        assertThat(model.cBoolean, is(false));
        assertThat(model.dBooleanValues.isEmpty(), is(true));
    }

    @Test
    public void testSetFalse_Argument_Equals_LongOpt() throws Exception {
        String[] args = {
                "--cBoolean=false"
        };
        BooleanConfigurations model = getPopulatedModel(args, BooleanConfigurations.class);
        assertThat(model.showHelp(), is(false));
        assertThat(model.cBoolean, is(false));
        assertThat(model.dBooleanValues.isEmpty(), is(true));
    }

    @Test
    public void testSetFalse_Argument_Space_LongOpt() throws Exception {
        String[] args = {
                "--cBoolean",
                "false"
        };
        BooleanConfigurations model = getPopulatedModel(args, BooleanConfigurations.class);
        assertThat(model.showHelp(), is(false));
        assertThat(model.cBoolean, is(false));
        assertThat(model.dBooleanValues.isEmpty(), is(true));
    }

    @Test
    public void testSetListTrueTrueFalse_Argument_Space_ShortOpt() throws Exception {
        String[] args = {
                "-d",
                "true,true,false"
        };
        BooleanConfigurations model = getPopulatedModel(args, BooleanConfigurations.class);
        assertThat(model.showHelp(), is(false));
        assertThat(model.cBoolean, is(false));
        assertThat(model.dBooleanValues.size(), is(3));
        assertThat(model.dBooleanValues.get(0), is(true));
        assertThat(model.dBooleanValues.get(1), is(true));
        assertThat(model.dBooleanValues.get(2), is(false));
    }

    @Test
    public void testSetListTrueTrueFalse_Argument_Space_LongOpt() throws Exception {
        String[] args = {
                "--dBoolean",
                "true,true,false"
        };
        BooleanConfigurations model = getPopulatedModel(args, BooleanConfigurations.class);
        assertThat(model.showHelp(), is(false));
        assertThat(model.cBoolean, is(false));
        assertThat(model.dBooleanValues.size(), is(3));
        assertThat(model.dBooleanValues.get(0), is(true));
        assertThat(model.dBooleanValues.get(1), is(true));
        assertThat(model.dBooleanValues.get(2), is(false));
    }

    @Test
    public void testSetListTrueTrueFalse_Argument_Equals_LongOpt() throws Exception {
        String[] args = {
                "--dBoolean=true,true,false"
        };
        BooleanConfigurations model = getPopulatedModel(args, BooleanConfigurations.class);
        assertThat(model.showHelp(), is(false));
        assertThat(model.cBoolean, is(false));
        assertThat(model.dBooleanValues.size(), is(3));
        assertThat(model.dBooleanValues.get(0), is(true));
        assertThat(model.dBooleanValues.get(1), is(true));
        assertThat(model.dBooleanValues.get(2), is(false));
    }

    @Test
    public void testDefaultTrue() throws Exception {
        String[] args = {
        };
        BooleanConfigurations model = getPopulatedModel(args, BooleanConfigurations.class);
        assertThat(model.showHelp(), is(false));
        assertThat(model.eBoolean, is(true));
        assertThat(model.dBooleanValues.isEmpty(), is(true));
    }

    @Test
    public void testDefaultTrueSetFalse_Argument_ShortOpt() throws Exception {
        String[] args = {
                "-e",
                "false"
        };
        BooleanConfigurations model = getPopulatedModel(args, BooleanConfigurations.class);
        assertThat(model.showHelp(), is(false));
        assertThat(model.eBoolean, is(false));
        assertThat(model.dBooleanValues.isEmpty(), is(true));
    }
}
 
