/*
 * Copyright (c) 2012 Geoff M. Granum
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

/*
 * Copyright 2012 Geoff M. Granum,All Rights Reserved.
 *
 * 11/2/12 4:35 PM 
 * @author ggranum
 */
package biz.granum.cli;

import biz.granum.cli.exception.*;
import java.lang.reflect.*;
import java.util.*;

public class CliArgumentProcessor<T> {


    private final String[] args;

    private final CliModelProcessor<T> modelProcessor;

    private CliArgumentProcessor(String[] args, CliModelProcessor<T> modelProcessor) {
        this.args = args;
        this.modelProcessor = modelProcessor;
    }

    public static <T> T processArguments(String[] args, CliModelProcessor<T> modelProcessor) {
        CliArgumentProcessor<T> processor = new CliArgumentProcessor<T>(args, modelProcessor);
        return processor.process();
    }


    private T process() {
        try {
            T model = modelProcessor.getModelClass().newInstance();
            modelProcessor.getProvider().processArguments(this.args);
            for (Field field : modelProcessor.getFields()) {
                CliOptionType optionType = modelProcessor.getCliOptionFor(field);
                CliArgumentType argumentType = modelProcessor.getCliOptionArgumentFor(field);
                boolean accessible = field.isAccessible();
                try {
                    field.setAccessible(true);
                    if(argumentType == null) {
                        handleNoArgumentOption(optionType.getOptionKey(), field, model);
                    } else if(argumentType.isRepeating()) {
                        handleRepeatingArgumentOption(optionType.getOptionKey(), field, argumentType, model);
                    } else {
                        handleArgumentOption(optionType.getOptionKey(), field, argumentType, model);
                    }
                } finally {
                    field.setAccessible(accessible);
                }
            }
            return model;
        } catch (IllegalAccessException e) {
            throw new CliCouldNotProcessArgumentsException(e);
        } catch (InstantiationException e) {
            throw new CliCouldNotCreateModelException("Model requires a public no-arg constructor.", e);
        }

    }

    private void handleNoArgumentOption(String optionKey, Field field, T model)
            throws IllegalAccessException {
        boolean flagSet = modelProcessor.getProvider().isOptionSet(optionKey);
        field.set(model, flagSet);
    }

    private void handleRepeatingArgumentOption(String optionKey, Field field,
                                               CliArgumentType argumentType, T model) throws IllegalAccessException {
        List list = modelProcessor.getProvider().getRepeatingOptionValue(optionKey, argumentType);
        Iterable values = convertListToFieldType(list, field);
        field.set(model, values);
    }

    private void handleArgumentOption(String optionKey, Field field, CliArgumentType argumentType,
                                      T model) throws IllegalAccessException {
        Object value = modelProcessor.getProvider().getOptionValue(optionKey, argumentType);
        if(value != null) {
            field.set(model, value);
        }
    }

    @SuppressWarnings("unchecked")
    private Collection convertListToFieldType(List list, Field field) {
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

