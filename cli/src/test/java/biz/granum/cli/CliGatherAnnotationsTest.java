/*
 * Copyright 2012 Geoff M. Granum,All Rights Reserved.
 *
 * 11/3/12 6:25 PM 
 * @author ggranum
 */
package biz.granum.cli;

import biz.granum.cli.configuration.errors.UninstantiableCollectionMemberType;
import biz.granum.cli.exception.CliCouldNotCreateDefaultValueException;
import java.io.PrintWriter;
import java.util.List;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CliGatherAnnotationsTest {

    public CliProviderPlugin<String[]> getPlugin() {
        return new CliProviderPlugin<String[]>() {

            //@Override
            public void addOption(CliOptionType optionType) {
            }

            //@Override
            public void addArgument(CliOptionType optionKey, CliArgumentType cliArgument) throws
                    CliCouldNotCreateDefaultValueException {
            }

            //@Override
            public void processInput(String[] args) {
            }

            //@Override
            public void printHelp(PrintWriter writer) {
            }

            //@Override
            public boolean isOptionSet(String optionKey) {
                return false;
            }

            //@Override
            public Object getOptionValue(String optionKey, CliArgumentType argumentType) {
                return null;
            }

            //@Override
            public List<Object> getRepeatingOptionValue(String optionKey, CliArgumentType argumentType) {
                return null;
            }
        };
    }

    public <T> T getPopulatedModel(String[] args, Class<T> model) throws Exception {
        CliProviderPlugin<String[]> plugin = getPlugin();
        CliModelProcessor<String[], T> processor = new CliModelProcessor<String[], T>(model, plugin, "", "") {
        };
        return processor.processInput(args);
    }

    @Test(expected = CliCouldNotCreateDefaultValueException.class)
    public void testUncreatableCollectionMemberType() throws Exception {
        String[] args = {};
        UninstantiableCollectionMemberType model = getPopulatedModel(args, UninstantiableCollectionMemberType.class);
        assertThat(model, nullValue());
    }

}
 
