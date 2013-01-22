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

package biz.granum.cli.joptsimple;

import biz.granum.cli.CliModelProcessor;
import biz.granum.cli.configuration.ShowHelpTestCase;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CliShowHelpTest {

    private String getHelpOutput(String header, String footer) throws Exception {
        String[] args = {
                "-h"
        };
        StringWriter stringWriter = new StringWriter();
        PrintWriter out = new PrintWriter(stringWriter);

        CliModelProcessor<String[], ShowHelpTestCase> processor =
                new JoptSimpleCliProcessor<ShowHelpTestCase>(
                        ShowHelpTestCase.class,
                        header, footer
                );
        ShowHelpTestCase config = processor.processInput(args);

        if(config.showHelp()) {
            processor.printHelp(out);
        }
        return stringWriter.getBuffer().toString().trim();

    }

    @Test
    public void testShowHelp() throws Exception {
        String expectedFooter = "Optional footer text.";
        String expectedHeader = "Optional header text.";
        String actual = getHelpOutput(expectedHeader, expectedFooter);
        assertThat(actual, startsWith(expectedHeader));
        assertThat(actual, endsWith(expectedFooter));
    }

    @Test
    public void testShowHelpForListOfStringsWithOnlyOptionDescription() throws Exception {
        String actual = getHelpOutput("", "");
        String expected = "\n-o, --other                             A comma separated list of values.      \r";
        assertThat(actual, containsString(expected));
    }

    @Test
    public void testShowHelpForListOfStringsWithOptionAndArgumentDescription() throws Exception {
        String actual = getHelpOutput("", "");
        String expected = "\n-t, --stringValues [foo,bar,baz]        A comma separated list of values,      \r\n" +
                          "                                          w/extra description.";
        assertThat(actual, containsString(expected));
    }
}
 
