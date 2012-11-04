/*
 * Copyright (c) 2012 Geoff M. Granum
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

/*
 * Copyright 2012 Geoff M. Granum,All Rights Reserved.
 *
 * 11/2/12 2:02 PM 
 * @author ggranum
 */
package biz.granum.cli.joptsimple;

import biz.granum.cli.*;
import biz.granum.cli.exception.*;
import java.io.*;
import java.util.*;
import joptsimple.*;

public class JoptSimpleCliProvider implements CliProviderPlugin {
    private final OptionParser optionParser;
    private OptionSpecBuilder builder;
    private OptionSet optionSet;

    public JoptSimpleCliProvider() {
        optionParser = new OptionParser();
    }

    public void addOption(CliOptionType optionType) {
        builder = optionParser.acceptsAll(Arrays.asList(
                optionType.getShortOption(), optionType.getLongOption()), optionType.getDescription()
        );
    }

    public void addArgument(CliOptionType optionKey, CliArgumentType cliArgument)
            throws CliCouldNotCreateDefaultValueException {
        try {
            ArgumentAcceptingOptionSpec optSpec;
            if(cliArgument.isRequired()) {
                optSpec = builder.withRequiredArg().ofType(cliArgument.getArgumentType());
            } else {
                optSpec = builder.withOptionalArg().ofType(cliArgument.getArgumentType());
                if(cliArgument.hasDefaultValues()) {
                    optSpec = defaultsToNoGenericsWarnings(cliArgument, optSpec);
                }
            }
            optSpec.describedAs(cliArgument.getDescription());
            if(optionKey.isRequired()) {
                optSpec.required();
            }


            if(cliArgument.isRepeating()) {
                optSpec.withValuesSeparatedBy(cliArgument.getSeparator());
            }

            if(cliArgument.getTypeConverter() != null
                    && ValueConverter.class.isAssignableFrom(cliArgument.getTypeConverter())) {
                withValuesConvertedByNoGenericsWarnings(cliArgument, optSpec);

            }
        } catch (Exception e) {
            throw new CliCouldNotProcessArgumentsException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private void withValuesConvertedByNoGenericsWarnings(CliArgumentType cliArgument,
                                                         ArgumentAcceptingOptionSpec optSpec)
            throws InstantiationException, IllegalAccessException {
        optSpec.withValuesConvertedBy((ValueConverter) cliArgument.getTypeConverter().newInstance());
    }


    @SuppressWarnings("unchecked")
    private ArgumentAcceptingOptionSpec defaultsToNoGenericsWarnings(CliArgumentType cliArgument,
                                                                     ArgumentAcceptingOptionSpec optSpec) {
        optSpec = optSpec.defaultsTo(cliArgument.getTypedDefaultValues());
        return optSpec;
    }

    public void processArguments(String[] arguments) {
        try {
            this.optionSet = optionParser.parse(arguments);
        } catch (OptionException e) {
            // Fragile, but why the *&(@! did jopt hide the exception class? Arrg!
            if(e.getClass().getName().endsWith("MissingRequiredOptionException")) {
                throw new CliMissingRequiredOptionException(e);
            }
            throw new CliUserInputException(e);
        }
    }


    public boolean isOptionSet(String optionKey) {
        return optionSet.has(optionKey);
    }

    public Object getOptionValue(String optionKey, CliArgumentType argumentType) {
        return optionSet.valueOf(optionKey);
    }

    public List getRepeatingOptionValue(String optionKey, CliArgumentType argumentType) {
        return optionSet.valuesOf(optionKey);
    }

    public void printHelp(PrintWriter writer) {
        try {
            optionParser.printHelpOn(writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
 
