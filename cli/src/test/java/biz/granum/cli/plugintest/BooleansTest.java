/*
 * Copyright 2012 Geoff M. Granum,All Rights Reserved.
 *
 * 11/3/12 6:25 PM 
 * @author ggranum
 */
package biz.granum.cli.plugintest;

import biz.granum.cli.annotation.CliOption;
import biz.granum.cli.annotation.CliOptionArgument;
import biz.granum.cli.configuration.BooleanConfigurations;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public abstract class BooleansTest<I> extends PluginTest<I> {

    @Test
    public void testShowHelp() throws Exception {
        I args = getTestData().getShowHelpFlag();
        BooleanConfigurations model = getPopulatedModel(args, BooleanConfigurations.class);
        assertThat(model.showHelp(), is(true));
        assertThat(model.booleanFlag, is(false));
        assertThat(model.listOfBooleans.isEmpty(), is(true));
    }

    /**
     * Enabling a flag should always provide the default value.
     * This is a little odd for boolean properties, as properties must actually set the value to true or false.
     * It makes more sense to think of a flag as a toggle switch for Properties based input; if the switch is 'on'
     * (true) then the default value will be used from the Configuration object.
     */
    @Test
    public void testBooleanFlagAndNoValue() throws Exception {
        PluginTestCase<I>[] inputs = getTestData().getFlagCases();
        for (PluginTestCase<I> testCase : inputs) {
            System.out.println(testCase.toString());
            BooleanWithNoArgument model = getPopulatedModel(testCase.input, BooleanWithNoArgument.class);
            assertThat(model.boolFlag, is(true));
        }

    }

    @Test
    public void testSetBooleanValueToTrue() throws Exception {
        PluginTestCase<I>[] inputs = getTestData().getSetBooleanToTrueCases();
        for (PluginTestCase<I> testCase : inputs) {
            System.out.println(testCase.toString());
            BooleanWithOptionalArgument model = getPopulatedModel(testCase.input, BooleanWithOptionalArgument.class);
            assertThat(model.boolWithOptionalArg, is(true));
        }
    }

    @Test
    public void testSetBooleanValueToFalse() throws Exception {
        PluginTestCase<I>[] inputs = getTestData().getSetBooleanToFalseCases();
        for (PluginTestCase<I> testCase : inputs) {
            System.out.println(testCase.toString());
            BooleanWithOptionalArgument model = getPopulatedModel(testCase.input, BooleanWithOptionalArgument.class);
            assertThat(model.boolWithOptionalArg, is(false));
        }
    }

    @Test
    public void testDefaultTrue() throws Exception {
        I args = getTestData().getEmptyInput();
        BooleanWithDefaultTrue model = getPopulatedModel(args, BooleanWithDefaultTrue.class);
        assertThat(model.boolWithDefaultOfTrue, is(true));
    }

    @Test
    public void testSetBooleanWithDefaultTrueToFalse() throws Exception {
        PluginTestCase<I>[] inputs = getTestData().getSetBooleanToFalseCases();
        for (PluginTestCase<I> testCase : inputs) {
            System.out.println(testCase.toString());
            BooleanWithDefaultTrue model =
                    getPopulatedModel(testCase.input, BooleanWithDefaultTrue.class);
            assertThat(model.boolWithDefaultOfTrue, is(false));
        }

    }

    /**
     * A boolean that takes no argument - also known as a flag.
     */
    public static class BooleanWithNoArgument {
        public BooleanWithNoArgument() {
        }

        @CliOption(
                shortOption = "a",
                longOption = "booleanLongOption",
                propertyKey = "booleanPropertyKey"
        )
        public Boolean boolFlag;
    }

    public static class BooleanWithOptionalArgument {
        public BooleanWithOptionalArgument() {
        }

        @CliOption(
                shortOption = "a",
                longOption = "booleanLongOption",
                propertyKey = "booleanPropertyKey",
                argument = @CliOptionArgument()
        )
        public Boolean boolWithOptionalArg;
    }

    public static class BooleanWithDefaultTrue {
        public BooleanWithDefaultTrue() {
        }

        @CliOption(
                shortOption = "a",
                longOption = "booleanLongOption",
                propertyKey = "booleanPropertyKey",
                description = "A single default=true boolean value.",
                argument = @CliOptionArgument(defaultValue = "true")
        )
        public Boolean boolWithDefaultOfTrue;
    }
}
 
