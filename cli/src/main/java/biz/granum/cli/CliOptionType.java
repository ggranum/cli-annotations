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
 * 11/2/12 3:32 PM 
 * @author ggranum
 */
package biz.granum.cli;

import biz.granum.cli.annotation.CliOptionArgument;
import biz.granum.cli.exception.CliMissingOptionNameAnnotationException;

public class CliOptionType {

    private final String shortOption;

    private final String longOption;

    private final String propertyKey;

    private final String optionLookup;

    private final String description;

    private final boolean required;

    private final CliOptionArgument argument;

    public CliOptionType(Builder builder) {
        this.shortOption = builder.shortOption;
        this.longOption = builder.longOption;
        this.propertyKey = builder.propertyKey;
        this.optionLookup = builder.optionLookup;
        this.required = builder.required;
        this.description = builder.description;
        this.argument = builder.argument;
    }

    public String getShortOption() {
        return shortOption;
    }

    public String getLongOption() {
        return longOption;
    }

    public String getPropertyKey() {
        return propertyKey;
    }

    public String getOptionLookupKey() {
        return optionLookup;
    }

    public String getDescription() {
        return description;
    }

    public boolean isRequired() {
        return required;
    }

    public CliOptionArgument getArgument() {
        return argument;
    }

    public static class Builder {
        private String shortOption;

        private String longOption;

        private String propertyKey;

        private String optionLookup;

        private String description;

        private boolean required;

        public CliOptionArgument argument;

        public Builder withPropertyKey(String propertyKey) {
            this.propertyKey = propertyKey;
            return this;
        }

        public Builder setShortOption(String shortOption) {
            this.shortOption = shortOption;
            return this;
        }

        public Builder setLongOption(String longOption) {
            this.longOption = longOption;
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

        public Builder setArgument(CliOptionArgument argument) {
            this.argument = argument;
            return this;
        }

        private void validateOptionName() {
            if(shortOption.equals("") && longOption.equals("")) {
                throw new CliMissingOptionNameAnnotationException();
            }
        }

        public CliOptionType build() {
            validateOptionName();
            optionLookup = longOption.equals("") ? shortOption : longOption;
            return new CliOptionType(this);
        }

    }
}
 
