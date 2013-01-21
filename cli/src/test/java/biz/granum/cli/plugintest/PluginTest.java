/*
 * Copyright 2013 Geoff M. Granum,All Rights Reserved.
 *
 * 1/20/13 5:38 PM 
 * @author ggranum
 */
package biz.granum.cli.plugintest;

import biz.granum.cli.CliModelProcessor;
import biz.granum.cli.TestData;

public abstract class PluginTest<I> {

    public abstract TestData<I> getTestData();

    public abstract <T> CliModelProcessor<I, T> getProcessor(Class<T> model,
            String header,
            String footer);

    public <T> T getPopulatedModel(I args, Class<T> model) throws Exception {
        CliModelProcessor<I, T> processor = getProcessor(model, "", "");
        return processor.processArguments(args);
    }

}
 
