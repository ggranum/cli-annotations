/*
 * Copyright 2013 Geoff M. Granum,All Rights Reserved.
 *
 * 1/20/13 3:43 PM 
 * @author ggranum
 */
package biz.granum.cli;

import biz.granum.cli.plugintest.PluginTestCase;

public abstract class TestData<I> {

    public abstract I getSimpleBoolean(Class modelClass, String fieldName, boolean value);

    public abstract I getSimpleStringList();

    public abstract PluginTestCase<I>[] getFlagCases();

    public abstract I getEmptyInput();

    public abstract I getTestUnknownArgument();

    public abstract I getShowHelpFlag();

    public abstract PluginTestCase<I>[] getSetBooleanToTrueCases();

    public abstract PluginTestCase<I>[] getSetBooleanToFalseCases();
}
 
