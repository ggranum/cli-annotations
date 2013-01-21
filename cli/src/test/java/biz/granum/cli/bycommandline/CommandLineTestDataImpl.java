/*
 * Copyright 2013 Geoff M. Granum,All Rights Reserved.
 *
 * 1/20/13 3:47 PM 
 * @author ggranum
 */
package biz.granum.cli.bycommandline;

import biz.granum.cli.TestData;

public class CommandLineTestDataImpl extends TestData<String[]> {

    @Override
    public String[] getSimpleStringList() {
        return new String[]{
                "-a",
                "foo,bar,baz"
        };
    }

    @Override
    public String[] getSimpleFlag() {
        return new String[]{
                "-s"
        };
    }

    @Override
    public String[] getEmptyInput() {
        return new String[0];
    }

    @Override
    public String[] getTestUnknownArgument() {
        return new String[]{
                "--aiksjdfkjasdf",
                "foo,bar,baz"
        };
    }

}
 
