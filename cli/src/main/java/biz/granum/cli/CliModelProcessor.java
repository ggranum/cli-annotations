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
 * 11/2/12 1:52 PM 
 * @author ggranum
 */
package biz.granum.cli;

import biz.granum.cli.annotation.*;
import biz.granum.cli.exception.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import org.apache.commons.lang.*;

public class CliModelProcessor<T> {

    private static final Map<Class, Class<? extends Collection>> collectionAbstractionMappings
            = new HashMap<Class, Class<? extends Collection>>();

    static {
        collectionAbstractionMappings.put(Set.class, HashSet.class);
        collectionAbstractionMappings.put(List.class, ArrayList.class);
        collectionAbstractionMappings.put(Iterable.class, ArrayList.class);
        collectionAbstractionMappings.put(SortedSet.class, TreeSet.class);
    }

    private final Class<T> modelClass;
    private final CliProviderPlugin provider;
    private Map<Field, CliOptionType> fieldToOptionsMap;
    private Map<Field, CliArgumentType> fieldToOptionArgumentTypeMap = new HashMap<Field, CliArgumentType>();
    private final String helpHeader;
    private final String helpFooter;


    private CliModelProcessor(Class<T> modelClass, CliProviderPlugin provider, String helpHeader, String helpFooter) {
        this.modelClass = modelClass;
        this.provider = provider;
        this.helpHeader = helpHeader;
        this.helpFooter = helpFooter;
    }

    public static void registerCollectionMapping(Class<? extends Collection> abstraction,
                                                 Class<? extends Collection> implementation) {
        collectionAbstractionMappings.put(abstraction, implementation);
    }

    public Class<T> getModelClass() {
        return modelClass;
    }

    public CliProviderPlugin getProvider() {
        return provider;
    }

    public Iterable<Field> getFields() {
        return fieldToOptionsMap.keySet();
    }

    public CliOptionType getCliOptionFor(Field field) {
        return fieldToOptionsMap.get(field);
    }

    public CliArgumentType getCliOptionArgumentFor(Field field) {
        return fieldToOptionArgumentTypeMap.get(field);
    }

    public static <T> CliModelProcessor<T> create(Class<T> modelClass, CliProviderPlugin provider,
                                                  String helpHeader, String helpFooter) {
        CliModelProcessor<T> modelProcessor = new CliModelProcessor<T>(modelClass, provider, helpHeader, helpFooter);
        modelProcessor.processModel();
        return modelProcessor;
    }

    public T processArguments(String[] arguments) throws CliCouldNotProcessArgumentsException {
        return CliArgumentProcessor.processArguments(arguments, this);
    }

    public void printHelp(PrintWriter writer) {
        if(helpHeader != null && helpHeader.length() > 0) {
            writer.println(helpHeader);
            writer.println();
        }
        this.provider.printHelp(writer);
        if(helpFooter != null && helpFooter.length() > 0) {
            writer.println();
            writer.println(helpFooter);
        }
        writer.flush();

    }

    private void processModel() {
        fieldToOptionsMap = getCliOptionFields(modelClass);
        for (Field field : fieldToOptionsMap.keySet()) {
            addOption(field, fieldToOptionsMap.get(field));
        }
    }

    private void addOption(Field field, CliOptionType optionType) {
        provider.addOption(optionType);
        if(hasArgument(field, optionType)) {
            addOptionArgument(field, optionType, optionType.getArgument());
        } else {

        }
    }

    private void addOptionArgument(Field field, CliOptionType optionKey, CliOptionArgument cliArgument) {
        String description = cliArgument.description();
        String[] defaultValues = null;
        Object[] typedDefaults = null;
        if(!cliArgument.required()) {
            defaultValues = cliArgument.defaultValue();
            typedDefaults = getTypedDefaults(getArgumentDataType(field), defaultValues);
        }
        validateArgumentType(field);
        Class typeConverter = cliArgument.typeConverter();
        CliArgumentType argType = new CliArgumentType.Builder()
                .setArgumentType(getArgumentDataType(field))
                .setDescription(description != null ? description : "")
                .setRequired(cliArgument.required())
                .setRepeating(Iterable.class.isAssignableFrom(field.getType()))
                .setDefaultValues(defaultValues)
                .setTypedDefaultValues(typedDefaults)
                .setSeparator(cliArgument.separator())
                .setTypeConverter(typeConverter == Object.class ? null : typeConverter)
                .build();
        this.fieldToOptionArgumentTypeMap.put(field, argType);
        provider.addArgument(optionKey, argType);
    }

    private void validateArgumentType(Field field) {
        Class<?> type = field.getType();
        if(Iterable.class.isAssignableFrom(type)) {
            getConcreteCollectionType(field);
        }
    }

    private static Class<?> getArgumentDataType(Field field) {
        Class<?> actualType;
        if(Collection.class.isAssignableFrom(field.getType())) {
            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
            actualType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        } else {
            actualType = field.getType();
        }
        return actualType;
    }

    private static boolean hasArgument(Field field, CliOptionType optionType) {
        //NullPointerException.class is a (hacky) marker to indicate that there are no arguments *explicitly set*.
        //non-flag fields will require an argument as well.
        boolean hasArgument = false;
        // longer than needed, but easier to read.
        if(optionType.getArgument().typeConverter() != NullPointerException.class) {
            hasArgument = true;
        } else if(field.getType() != boolean.class && field.getType() != Boolean.class) {
            hasArgument = true;
        }
        return hasArgument;

    }

    private static Map<Field, CliOptionType> getCliOptionFields(Class modelClass) {
        Map<Field, CliOptionType> fieldMap = new HashMap<Field, CliOptionType>();
        do {
            Field[] fields = modelClass.getDeclaredFields();
            for (Field field : fields) {
                CliOption cliOption = field.getAnnotation(CliOption.class);

                if(cliOption != null) {
                    CliOptionType optionType = new CliOptionType.Builder()
                            .setShortOption(cliOption.shortOption())
                            .setLongOption(cliOption.longOption())
                            .setDescription(cliOption.description())
                            .setRequired(cliOption.required())
                            .setArgument(cliOption.argument())
                            .build();
                    fieldMap.put(field, optionType);
                }
            }
        } while ((modelClass = modelClass.getSuperclass()) != null && modelClass != Object.class);
        return fieldMap;
    }

    private static Object[] getTypedDefaults(Class<?> type, String... defaultValues) {
        Method method;
        Object[] typedDefaultValues = new Object[defaultValues.length];
        String currentDefaultValue = null; // for error message.
        try {
            if(type == String.class) {
                for (int i = 0, defaultValuesLength = defaultValues.length; i < defaultValuesLength; i++) {
                    currentDefaultValue = defaultValues[i];
                    typedDefaultValues[i] = currentDefaultValue;
                }
            } else {
                if(type.isPrimitive()) {
                    type = ClassUtils.primitiveToWrapper(type);
                }
                method = type.getMethod("valueOf", String.class);
                for (int i = 0, defaultValuesLength = defaultValues.length; i < defaultValuesLength; i++) {
                    currentDefaultValue = defaultValues[i];
                    typedDefaultValues[i] = method.invoke(null, currentDefaultValue);
                }
            }

        } catch (InvocationTargetException e) {
            String msg = String.format("Could not create value of type %s from the value %s",
                    type, currentDefaultValue);
            throw new CliCouldNotCreateDefaultValueException(msg, e);
        } catch (NoSuchMethodException e) {
            String msg = "Defaults only implemented for types that implement a method with the signature " +
                    "\"public static {type} valueOf(String);\"";
            throw new CliCouldNotCreateDefaultValueException(msg, e);
        } catch (IllegalAccessException e) {
            throw new CliCouldNotCreateDefaultValueException("I'm sorry, Dave.", e);
        }
        return typedDefaultValues;

    }

    Class getConcreteCollectionType(Field field) {
        Class type = field.getType();
        if(!type.isInterface() && !Modifier.isAbstract(type.getModifiers())) {
            return type;
        }
        Class<? extends Collection> concreteType = collectionAbstractionMappings.get(type);
        if(concreteType == null) {
            String msg = String.format("No concrete collection class registered for abstract type '%s'",
                    type.getName());
            throw new CliCouldNotCreateCollectionException(msg);
        }
        return concreteType;
    }

}
 