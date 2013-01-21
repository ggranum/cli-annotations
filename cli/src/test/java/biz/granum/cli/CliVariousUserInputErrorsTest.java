/*
 * Copyright 2012 Geoff M. Granum,All Rights Reserved.
 *
 * 11/3/12 6:25 PM 
 * @author ggranum
 */
package biz.granum.cli;

import biz.granum.cli.configuration.BooleanConfigurations;
import biz.granum.cli.exception.CliUserInputException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public abstract class CliVariousUserInputErrorsTest<I> {

    private TestData<I> testData;

    public abstract TestData<I> getTestData();

    public abstract <T> CliModelProcessor<I, T> getProcessor(Class<T> model,
            String header,
            String footer);

    public <T> T getPopulatedModel(I args, Class<T> model) throws Exception {
        CliModelProcessor<I, T> processor = getProcessor(model, "", "");
        return processor.processArguments(args);
    }

    @Before
    public void setUp() throws Exception {
        this.testData = getTestData();
    }

    @Test(expected = CliUserInputException.class)
    public void testUnknownArgument() throws Exception {
        I args = testData.getTestUnknownArgument();
        BooleanConfigurations model = getPopulatedModel(args, BooleanConfigurations.class);
        assertThat(model, nullValue());
    }

}
 
