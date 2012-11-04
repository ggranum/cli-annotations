/*
 * Copyright 2012 Geoff M. Granum,All Rights Reserved.
 *
 * 11/3/12 6:25 PM 
 * @author ggranum
 */
package biz.granum.cli;

import biz.granum.cli.configuration.errors.*;
import biz.granum.cli.exception.*;
import java.io.*;
import java.util.*;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class CliGatherAnnotationsTest {


    public CliProviderPlugin getPlugin() {
        return new CliProviderPlugin() {

            @Override
            public void addOption(CliOptionType optionType) {
            }

            @Override
            public void addArgument(CliOptionType optionKey, CliArgumentType cliArgument) throws CliCouldNotCreateDefaultValueException {
            }

            @Override
            public void processArguments(String[] args) {
            }

            @Override
            public void printHelp(PrintWriter writer) {
            }

            @Override
            public boolean isOptionSet(String optionKey) {
                return false;
            }

            @Override
            public Object getOptionValue(String optionKey, CliArgumentType argumentType) {
                return null;
            }

            @Override
            public List<Object> getRepeatingOptionValue(String optionKey, CliArgumentType argumentType) {
                return null;
            }
        };
    }

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

    @Test(expected = CliMissingOptionNameAnnotationException.class)
    public void testHandleMissingOptions() throws Exception {
        String[] args = {};
        MissingOptionsConfiguration model = getPopulatedModel(args, MissingOptionsConfiguration.class);
        assertThat(model, nullValue());
    }

    @Test(expected = CliCouldNotCreateDefaultValueException.class)
    public void testUncreatableCollectionMemberType() throws Exception {
        String[] args = {};
        UninstantiableCollectionMemberType model = getPopulatedModel(args, UninstantiableCollectionMemberType.class);
        assertThat(model, nullValue());
    }


}
 
