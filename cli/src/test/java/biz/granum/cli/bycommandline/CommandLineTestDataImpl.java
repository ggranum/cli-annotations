/*
 * Copyright 2013 Geoff M. Granum,All Rights Reserved.
 *
 * 1/20/13 3:47 PM 
 * @author ggranum
 */
package biz.granum.cli.bycommandline;

import biz.granum.cli.TestData;

public class CommandLineTestDataImpl extends TestData<String[]> {

    private CommandLineTestCase getCase(String title, String example, String... ary) {
        return new CommandLineTestCase(title, example, ary);
    }

    @Override
    public String[] getSimpleBoolean(Class modelClass, String fieldName, boolean value) {
        return new String[]{"--" + fieldName, String.valueOf(value)};
    }

    @Override
    public String[] getSimpleStringList() {
        return new String[]{"-a", "foo,bar,baz"};
    }

    @Override
    public CommandLineTestCase[] getFlagCases() {
        return new CommandLineTestCase[]{
                getCase("By short option.", "foo.exe -a", "-a"),
                getCase("By long option.", "foo.exe --booleanLongOption", "--booleanLongOption")
        };
    }

    @Override
    public CommandLineTestCase[] getSetBooleanToTrueCases() {
        return new CommandLineTestCase[]{
                getCase("By short option and explicit value.", "foo.exe -a true", "-a", "true"),
                getCase("By short option and explicit value with equals.", "foo.exe -a=true", "-a=true"),
                getCase("By long option and explicit value.",
                        "foo.exe --booleanLongOption true", "--booleanLongOption", "true"),
                getCase("By long option and explicit value with equals.",
                        "foo.exe --booleanLongOption=true", "--booleanLongOption=true")
        };
    }

    @Override
    public CommandLineTestCase[] getSetBooleanToFalseCases() {
        return new CommandLineTestCase[]{
                getCase("By short option and explicit value.", "foo.exe -a false", "-a", "false"),
                getCase("By short option and explicit value.", "foo.exe -a=false", "-a=false"),
                getCase("By long option and explicit value.",
                        "foo.exe --booleanLongOption false", "--booleanLongOption", "false"),
                getCase("By long option and explicit value.",
                        "foo.exe --booleanLongOption=false", "--booleanLongOption=false")
        };
    }

    @Override
    public String[] getEmptyInput() {
        return new String[0];
    }

    @Override
    public String[] getTestUnknownArgument() {
        return new String[]{"--aiksjdfkjasdf", "foo,bar,baz"};
    }

    @Override
    public String[] getShowHelpFlag() {
        return new String[]{"-h"};
    }

}
 
