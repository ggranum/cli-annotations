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

package biz.granum.cli.configuration;

import biz.granum.cli.CliConfig;
import biz.granum.cli.annotation.CliOption;
import biz.granum.cli.annotation.CliOptionArgument;
import java.util.List;

public class BooleanConfigurations extends CliConfig {

    @CliOption(
            shortOption = "b",
            longOption = "bBoolean",
            description = "A single boolean value"
    )
    public boolean bBoolean;

    @CliOption(
            shortOption = "c",
            longOption = "cBoolean",
            description = "A single boolean value with optional args.",
            argument = @CliOptionArgument()
    )
    public boolean cBoolean;

    @CliOption(
            shortOption = "d",
            longOption = "dBooleanValues",
            description = "A list of boolean values"
    )
    public List<Boolean> dBooleanValues;

    @CliOption(
            shortOption = "e",
            longOption = "eBoolean",
            description = "A single default=true boolean value.",
            argument = @CliOptionArgument(defaultValue = "true")
    )
    public Boolean eBoolean;

    @CliOption(
            shortOption = "f",
            longOption = "fBooleanLongOption",
            description = "Boolean value with long option, default false."
    )
    public Boolean fBoolean;

    @CliOption(
            shortOption = "g",
            description = "Boolean value with no long option."
    )
    public Boolean gBoolean;

    @CliOption(
            shortOption = "i",
            longOption = "iBoolean",
            propertyKey = "custom.key.for.i.boolean",
            description = "Boolean value with custom key"
    )
    public Boolean iBoolean;

    public BooleanConfigurations() {
    }

}
 
