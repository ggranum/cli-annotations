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
package biz.granum.cli.preferences;

import biz.granum.cli.CliArgumentType;
import biz.granum.cli.CliOptionType;
import biz.granum.cli.CliProviderPlugin;
import biz.granum.cli.exception.CliCouldNotCreateDefaultValueException;
import biz.granum.cli.exception.CliMissingRequiredOptionException;
import biz.granum.cli.exception.CliUserInputException;
import biz.granum.cli.util.BasicReflection;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PreferencesConfigurationProvider implements CliProviderPlugin<Properties> {

    Map<String, CliOptionType> optionLookupKeyToCliOption = new HashMap<String, CliOptionType>();

    Map<String, String> values;

    public PreferencesConfigurationProvider() {
    }

    public void addOption(CliOptionType optionType) {
        optionLookupKeyToCliOption.put(optionType.getOptionLookupKey(), optionType);
    }

    public void addArgument(CliOptionType optionKey, CliArgumentType cliArgument)
            throws CliCouldNotCreateDefaultValueException {
    }

    public void processInput(Properties input) {
        for (CliOptionType optionType : optionLookupKeyToCliOption.values()) {
            String propKey = optionType.getPropertyKey();
            if(optionType.isRequired() && !input.containsKey(propKey)) {
                String msg = String.format("Property %s is required, but is missing from the provided input.",
                        propKey);
                throw new CliMissingRequiredOptionException(msg);
            }
        }
        try {
            values = asMap(input);

        } catch (Exception e) {
            //
            throw new CliUserInputException(e);
        }
    }

    private Map<String, String> asMap(Properties input) {
        //noinspection UnnecessaryLocalVariable
        @SuppressWarnings("unchecked")
        Map<String, String> cast = Map.class.cast(input);
        return cast;
    }

    public boolean isOptionSet(String optionLookupKey) {
        String value = getRawValue(optionLookupKey);
        return Boolean.valueOf(value);
    }

    public Object getOptionValue(String optionLookupKey, CliArgumentType argumentType) {
        String value = getRawValue(optionLookupKey);
        if(value == null) {
            value = defaultValue(argumentType);
        }
        return toTypedValue(argumentType.getArgumentType(), value);
    }

    private String getRawValue(String optionLookupKey) {
        return values.get(optionLookupKeyToCliOption.get(optionLookupKey).getPropertyKey());
    }

    private Object toTypedValue(Class<?> argumentType, String value) {
        return BasicReflection.typedValuesFromStrings(argumentType, value)[0];
    }

    private String defaultValue(CliArgumentType argumentType) {
        String[] defaults = argumentType.getDefaultValues();
        if(defaults != null && defaults.length == 1) {
            return defaults[0];
        }
        return null;
    }

    public List getRepeatingOptionValue(String optionLookupKey, CliArgumentType argumentType) {
        String[] values;
        String raw = getRawValue(optionLookupKey);
        values = raw != null
                ? raw.split(String.valueOf(argumentType.getSeparator()))
                : argumentType.getDefaultValues();
        if(values == null) {
            return null;
        }
        return Arrays.asList(BasicReflection.typedValuesFromStrings(argumentType.getArgumentType(), values));
    }

    public void printHelp(PrintWriter writer) {
        writer.println("Help not (yet) implemented for properties reader.");
    }
}
 
