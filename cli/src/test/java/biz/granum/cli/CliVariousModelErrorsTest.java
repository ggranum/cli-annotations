/*
 * Copyright 2012 Geoff M. Granum,All Rights Reserved.
 *
 * 11/3/12 6:25 PM 
 * @author ggranum
 */
package biz.granum.cli;

import biz.granum.cli.configuration.errors.*;
import biz.granum.cli.exception.*;
import java.util.*;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public abstract class CliVariousModelErrorsTest {


    public abstract CliProviderPlugin getPlugin();

    public <T> T getPopulatedModel(String[] args, Class<T> model) {
        CliProviderPlugin plugin = getPlugin();
        CliModelProcessor<T> processor = CliModelProcessor.create(
                model,
                plugin,
                "",
                ""
        );
        return processor.processArguments(args);
    }

    @Test(expected = CliCouldNotCreateCollectionException.class)
    public void testUnmappedCollectionType() throws Exception {
        String[] args = {
                "-a",
                "foo,bar,baz"
        };
        UnmappedCollectionType model = getPopulatedModel(args, UnmappedCollectionType.class);
        assertThat(model, nullValue());
    }

    @Test
    public void testManuallyMappedCollectionType() throws Exception {
        String[] args = {
                "-a",
                "foo,bar,baz"
        };
        CliModelProcessor.registerCollectionMapping(AbstractList.class, ArrayList.class);
        UnmappedCollectionType model = getPopulatedModel(args, UnmappedCollectionType.class);
        assertThat(model, notNullValue());
    }

    @Test(expected = CliCouldNotCreateCollectionException.class)
    public void testUninstantiableCollectionType() throws Exception {
        String[] args = {
                "-a",
                "foo,bar,baz"
        };
        UninstantiableCollectionType model = getPopulatedModel(args, UninstantiableCollectionType.class);
        assertThat(model, nullValue());
    }

    @Test(expected = CliCouldNotCreateCollectionException.class)
    public void testInstantiationExceptionCollectionType() throws Exception {
        String[] args = {
                "-a",
                "foo,bar,baz"
        };
        InstantiationExceptionCollectionType model =
                getPopulatedModel(args, InstantiationExceptionCollectionType.class);
        assertThat(model, nullValue());
    }

    @Test(expected = CliCouldNotCreateModelException.class)
    public void testUninstantiableModelType() throws Exception {
        String[] args = {
                "-s"
        };
        UninstantiableModelType model = getPopulatedModel(args, UninstantiableModelType.class);
        assertThat(model, nullValue());
    }

    @Test(expected = CliMissingRequiredOptionException.class)
    public void testMissingRequiredValue() throws Exception {
        String[] args = {

        };
        RequiredIntegerConfiguration model = getPopulatedModel(args, RequiredIntegerConfiguration.class);
        assertThat(model, nullValue());
    }

    @Test(expected = CliCouldNotCreateDefaultValueException.class)
    public void testBadDefaultValue() throws Exception {
        String[] args = {

        };
        BadDefaultValuesConfiguration model = getPopulatedModel(args, BadDefaultValuesConfiguration.class);
        assertThat(model, nullValue());
    }


}
 
