/*
 * Copyright 2012 Geoff M. Granum,All Rights Reserved.
 *
 * 11/3/12 6:25 PM 
 * @author ggranum
 */
package biz.granum.cli.plugintest;

import biz.granum.cli.CliModelProcessor;
import biz.granum.cli.TestData;
import biz.granum.cli.configuration.errors.BadDefaultValuesConfiguration;
import biz.granum.cli.configuration.errors.InstantiationExceptionCollectionType;
import biz.granum.cli.configuration.errors.RequiredIntegerConfiguration;
import biz.granum.cli.configuration.errors.UninstantiableCollectionType;
import biz.granum.cli.configuration.errors.UninstantiableModelType;
import biz.granum.cli.configuration.errors.UnmappedCollectionType;
import biz.granum.cli.exception.CliCouldNotCreateCollectionException;
import biz.granum.cli.exception.CliCouldNotCreateDefaultValueException;
import biz.granum.cli.exception.CliCouldNotCreateModelException;
import biz.granum.cli.exception.CliMissingRequiredOptionException;
import java.util.AbstractList;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public abstract class VariousModelErrorsTest<I> {

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

    @Test(expected = CliCouldNotCreateCollectionException.class)
    public void testUnmappedCollectionType() throws Exception {
        I args = testData.getSimpleStringList();
        UnmappedCollectionType model = getPopulatedModel(args, UnmappedCollectionType.class);
        assertThat(model, nullValue());
    }

    @Test
    public void testManuallyMappedCollectionType() throws Exception {
        I args = testData.getSimpleStringList();
        CliModelProcessor.registerCollectionMapping(AbstractList.class, ArrayList.class);
        UnmappedCollectionType model = getPopulatedModel(args, UnmappedCollectionType.class);
        assertThat(model, notNullValue());
        assertThat(model.unmappedCollectionType.size(), is(3));
    }

    @Test(expected = CliCouldNotCreateCollectionException.class)
    public void testUninstantiableCollectionType() throws Exception {
        I args = testData.getSimpleStringList();
        UninstantiableCollectionType model = getPopulatedModel(args, UninstantiableCollectionType.class);
        assertThat(model, nullValue());
    }

    @Test(expected = CliCouldNotCreateCollectionException.class)
    public void testInstantiationExceptionCollectionType() throws Exception {
        I args = testData.getSimpleStringList();
        InstantiationExceptionCollectionType model =
                getPopulatedModel(args, InstantiationExceptionCollectionType.class);
        assertThat(model, nullValue());
    }

    @Test(expected = CliCouldNotCreateModelException.class)
    public void testUninstantiableModelType() throws Exception {
        I args = testData.getSimpleFlag();
        UninstantiableModelType model = getPopulatedModel(args, UninstantiableModelType.class);
        assertThat(model, nullValue());
    }

    @Test(expected = CliMissingRequiredOptionException.class)
    public void testMissingRequiredValue() throws Exception {
        I args = testData.getEmptyInput();
        RequiredIntegerConfiguration model = getPopulatedModel(args, RequiredIntegerConfiguration.class);
        assertThat(model, nullValue());
    }

    @Test(expected = CliCouldNotCreateDefaultValueException.class)
    public void testBadDefaultValue() throws Exception {
        I args = testData.getEmptyInput();
        BadDefaultValuesConfiguration model = getPopulatedModel(args, BadDefaultValuesConfiguration.class);
        assertThat(model, nullValue());
    }
}
 
