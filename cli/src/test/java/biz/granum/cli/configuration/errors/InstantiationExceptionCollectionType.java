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

package biz.granum.cli.configuration.errors;

import biz.granum.cli.*;
import biz.granum.cli.annotation.*;
import java.util.*;

public class InstantiationExceptionCollectionType extends CliConfig {

    @CliOption(
            shortOption = "a",
            longOption = "aBadCollectionType",
            description = "A bad collection type."
    )
    public CustomListType<String> runtimeExceptionList;


    public InstantiationExceptionCollectionType() {
    }

    public static class CustomListType<T> extends ArrayList<T> {

        public CustomListType(int initialCapacity) {
            super(initialCapacity);
        }

        public CustomListType(Collection<? extends T> c) {
            super(c);
        }
    }
}
 
