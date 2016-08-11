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
package biz.granum.cli;

import org.apache.commons.lang.*;

public class CliArgumentType {

    private final Class<?> argumentType;

    private final boolean required;

    private final String description; // default "";

    private final String[] defaultValues;// default {};

    private final Object[] typedDefaultValues;// default {};

    private final char separator;// default '\u0000';

    private final Class typeConverter;// default Object.class;

    private final boolean repeating;

    public CliArgumentType(Builder builder) {
        this.argumentType = builder.argumentType;
        this.required = builder.required;
        this.description = builder.description;
        this.defaultValues = builder.defaultValues;
        this.typedDefaultValues = builder.typedDefaultValues;
        this.separator = builder.separator;
        this.typeConverter = builder.typeConverter;
        this.repeating = builder.repeating;
    }

    public Class<?> getArgumentType() {
        return argumentType;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isRepeating() {
        return repeating;
    }

    public String getDescription() {
        return description;
    }

    public String[] getDefaultValues() {
        return defaultValues;
    }

    public Object[] getTypedDefaultValues() {
        return typedDefaultValues;
    }

    public char getSeparator() {
        return separator;
    }

    public Class getTypeConverter() {
        return typeConverter;
    }

    public boolean hasDefaultValues() {
        return this.defaultValues.length > 0;
    }

    public static class Builder {
        private boolean required;

        private String description;

        private String[] defaultValues;

        private char separator;

        private Class typeConverter;// default Object.class;
        public Object[] typedDefaultValues;
        private Class<?> argumentType;
        private boolean repeating;


        public Builder setArgumentType(Class<?> argumentType) {
            this.argumentType = argumentType;
            return this;
        }

        public Builder setRepeating(boolean repeating) {
            this.repeating = repeating;
            return this;
        }

        public Builder setRequired(boolean required) {
            this.required = required;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setDefaultValues(String[] defaultValues) {
            this.defaultValues = defaultValues;
            return this;
        }

        public Builder setTypedDefaultValues(Object[] typedDefaultValues) {
            this.typedDefaultValues = typedDefaultValues;
            return this;
        }

        public Builder setSeparator(char separator) {
            this.separator = separator;
            return this;
        }

        public Builder setTypeConverter(Class typeConverter) {
            this.typeConverter = typeConverter == Object.class ? null : typeConverter;
            return this;
        }

        public CliArgumentType build() {
            Validate.isTrue(!required || (defaultValues == null || defaultValues.length == 0));
            return new CliArgumentType(this);
        }

    }
}
 
