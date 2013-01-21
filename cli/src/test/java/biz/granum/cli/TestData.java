/*
 * Copyright 2013 Geoff M. Granum,All Rights Reserved.
 *
 * 1/20/13 3:43 PM 
 * @author ggranum
 */
package biz.granum.cli;

public abstract class TestData<I> {

    public abstract I getSimpleStringList();

    public abstract I getSimpleFlag();

    public abstract I getEmptyInput();

    public abstract I getTestUnknownArgument();
}
 
