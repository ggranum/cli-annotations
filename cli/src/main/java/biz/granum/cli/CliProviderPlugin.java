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
 * 11/2/12 2:02 PM 
 * @author ggranum
 */
package biz.granum.cli;

import biz.granum.cli.exception.*;
import java.io.*;
import java.util.*;

public interface CliProviderPlugin {
    void addOption(CliOptionType optionType);

    void addArgument(CliOptionType optionKey, CliArgumentType cliArgument) throws CliCouldNotCreateDefaultValueException;

    void processArguments(String[] args);

    void printHelp(PrintWriter writer);

    /**
     * Handle a no-arg option value, often referred to as 'flags'.
     *
     * @param optionKey The short or long option, such as 'h' or 'help'.
     * @return True if the no-argument option was present in the arguments list, false otherwise.
     */
    boolean isOptionSet(String optionKey);

    Object getOptionValue(String optionKey, CliArgumentType argumentType);

    List getRepeatingOptionValue(String optionKey, CliArgumentType argumentType);

}
