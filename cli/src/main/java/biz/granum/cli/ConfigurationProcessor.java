/*
 * Copyright 2013 Geoff M. Granum,All Rights Reserved.
 *
 * 1/19/13 3:21 PM 
 * @author ggranum
 */
package biz.granum.cli;

import biz.granum.cli.exception.CliCouldNotCreateCollectionException;
import biz.granum.cli.exception.CliCouldNotProcessArgumentsException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

public class ConfigurationProcessor<I, T> {

    private final CliModelProcessor<I, T> modelProcessor;

    private final CliProviderPlugin<I> provider;

    protected ConfigurationProcessor(CliModelProcessor<I, T> modelProcessor,
            CliProviderPlugin<I> provider) {
        this.modelProcessor = modelProcessor;
        this.provider = provider;
    }

    T process(I input, T model) {
        try {
            provider.processInput(input);
            for (Field field : modelProcessor.getFields()) {
                CliOptionType optionType = modelProcessor.getCliOptionFor(field);
                CliArgumentType argumentType = modelProcessor.getCliOptionArgumentFor(field);
                boolean accessible = field.isAccessible();
                try {
                    field.setAccessible(true);
                    if(argumentType == null) {
                        handleNoArgumentOption(optionType.getOptionLookupKey(), field, model);
                    } else if(argumentType.isRepeating()) {
                        handleRepeatingArgumentOption(optionType.getOptionLookupKey(), field, argumentType, model);
                    } else {
                        handleArgumentOption(optionType.getOptionLookupKey(), field, argumentType, model);
                    }
                } finally {
                    field.setAccessible(accessible);
                }
            }
            return model;
        } catch (IllegalAccessException e) {
            throw new CliCouldNotProcessArgumentsException(e);
        }

    }

    protected void handleNoArgumentOption(String optionKey, Field field, T model)
            throws IllegalAccessException {
        boolean flagSet = provider.isOptionSet(optionKey);
        field.set(model, flagSet);
    }

    protected void handleRepeatingArgumentOption(String optionKey, Field field,
            CliArgumentType argumentType, T model)
            throws IllegalAccessException {
        List list = provider.getRepeatingOptionValue(optionKey, argumentType);
        Iterable values = convertListToFieldType(list, field);
        field.set(model, values);

    }

    protected void handleArgumentOption(String optionKey, Field field, CliArgumentType argumentType,
            T model) throws IllegalAccessException {
        Object value = provider.getOptionValue(optionKey, argumentType);
        if(value != null) {
            field.set(model, value);
        }
    }

    @SuppressWarnings("unchecked")
    protected Collection convertListToFieldType(List list, Field field) {
        try {
            Class collectionType = modelProcessor.getConcreteCollectionType(field);
            Collection collection = Collection.class.cast(collectionType.newInstance());
            if(list != null && list.size() > 0) {
                collection.addAll(list);
            }
            return collection;
        } catch (InstantiationException e) {
            String msg = String.format("Could not create a collection of type %s for field %s",
                    field.getType(), field.getName());
            throw new CliCouldNotCreateCollectionException(msg, e);
        } catch (IllegalAccessException e) {
            String msg = String.format("Could not create a collection of type %s for field %s",
                    field.getType(), field.getName());
            throw new CliCouldNotCreateCollectionException(msg, e);
        }
    }
}

