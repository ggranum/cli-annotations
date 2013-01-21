/*
 * Copyright 2013 Geoff M. Granum,All Rights Reserved.
 *
 * 1/21/13 10:07 AM 
 * @author ggranum
 */
package biz.granum.cli.propreader;

import biz.granum.cli.annotation.CliOption;
import biz.granum.cli.annotation.CliOptionArgument;
import java.util.List;

public class ProcessorTestConfig {

    @CliOption(
            shortOption = "b",
            longOption = "booleanLongOption",
            propertyKey = "booleanPropertyKey",
            description = "A single boolean value"
    )
    public boolean booleanValue;

    @CliOption(
            shortOption = "i",
            longOption = "integerLongOption",
            propertyKey = "integerPropertyKey",
            description = "Simplest integer value."
    )
    public int integerValue;

    @CliOption(
            shortOption = "j",
            longOption = "integerListLongOption",
            propertyKey = "integerListPropertyKey",
            description = "List of integer values.",
            argument = @CliOptionArgument()
    )
    public List<Integer> integerList;

    @CliOption(
            shortOption = "k",
            longOption = "integerListDefaultsLongOption",
            propertyKey = "integerListDefaultsPropertyKey",
            description = "List of integer values with defaults.",
            argument = @CliOptionArgument(defaultValue = {"10", "20", "30"})
    )
    public List<Integer> integerListWithDefaults;

    @CliOption(
            shortOption = "f",
            longOption = "floatLongOption",
            propertyKey = "floatPropertyKey",
            description = "A single float with a default value.",
            argument = @CliOptionArgument()
    )
    public Float floatValue;

    @CliOption(
            shortOption = "g",
            longOption = "floatWithDefaultLongOption",
            propertyKey = "floatWithDefaultPropertyKey",
            description = "A single float with a default value.",
            argument = @CliOptionArgument(defaultValue = "30.0")
    )
    public Float floatWithDefault;

    @CliOption(
            shortOption = "s",
            longOption = "stringValueLongOption",
            propertyKey = "stringValuePropertyKey",
            description = "A single string value.",
            argument = @CliOptionArgument()
    )
    public String stringValue;

    @CliOption(
            shortOption = "t",
            longOption = "stringListWithDefaultLongOption",
            propertyKey = "stringListWithDefaultPropertyKey",
            description = "A list of strings with defaults.",
            argument = @CliOptionArgument(defaultValue = {"foo", "bar,baz", "qux"})
    )
    public List<String> stringListWithDefaults;

}
 
