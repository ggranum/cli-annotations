/*
 * Copyright 2013 Geoff M. Granum,All Rights Reserved.
 *
 * 1/20/13 1:46 PM 
 * @author ggranum
 */
package biz.granum.cli.util;

import biz.granum.cli.exception.CliCouldNotCreateDefaultValueException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.commons.lang.ClassUtils;

public class BasicReflection {
    public static Object[] typedValuesFromStrings(Class<?> type, String... defaultValues) {
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
                    typedDefaultValues[i] = getInstanceValue(type, method, currentDefaultValue);
                }
            }

        } catch (InvocationTargetException e) {
            String msg = String.format("Could not create value of type %s from the value %s",
                    type, currentDefaultValue);
            throw new CliCouldNotCreateDefaultValueException(msg, e);
        } catch (NoSuchMethodException e) {
            String msg = "Dynamic type construction only implemented for types that implement a method with the " +
                         "signature \"public static {type} valueOf(String);\"";
            throw new CliCouldNotCreateDefaultValueException(msg, e);
        } catch (IllegalAccessException e) {
            throw new CliCouldNotCreateDefaultValueException("I'm sorry, Dave.", e);
        }
        return typedDefaultValues;
    }

    private static Object getInstanceValue(Class<?> type, Method method, String currentDefaultValue) throws
            IllegalAccessException,
            InvocationTargetException {
        if(currentDefaultValue == null || currentDefaultValue.isEmpty()) {
            currentDefaultValue = getDefaultForType(type);
        }
        return method.invoke(null, currentDefaultValue);
    }

    private static String getDefaultForType(Class<?> type) {
        if(Number.class.isAssignableFrom(type)) {
            return "0";
        } else if(Boolean.class.isAssignableFrom(type)) {
            return "false";
        }
        return "";
    }
}
 
