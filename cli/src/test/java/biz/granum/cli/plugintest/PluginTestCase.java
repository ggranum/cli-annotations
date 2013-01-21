/*
 * Copyright 2013 Geoff M. Granum,All Rights Reserved.
 *
 * 1/20/13 7:08 PM 
 * @author ggranum
 */
package biz.granum.cli.plugintest;

public abstract class PluginTestCase<I> {

    private final String title;

    private final String example;

    public final I input;

    public PluginTestCase(String title, String example, I input) {
        this.title = title;
        this.example = example;
        this.input = input;
    }

    public String toString() {
        return String.format("TestCase: %s\n     example: %s", title, example);
    }

}
 
