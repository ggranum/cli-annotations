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

package biz.granum.cli.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CliOption {

    /**
     * For command lines, usually the single letter version of the option.
     * For example, specifying a short option of 'h' and a long option of 'help': for a help flag would result in
     * a command line like: 'foo.exe -h' using the short option, and 'foo.exe --help' using the long option.
     */
    String shortOption() default "";

    /**
     * For command lines, usually the full-word version of the option.
     * For example, specifying a short option of 'h' and a long option of 'help': for a help flag would result in
     * a command line like: 'foo.exe -h' using the short option, and 'foo.exe --help' using the long option.
     */
    String longOption() default "";

    /**
     * A key to use for property file (or System.property/System.env) based configuration readers.
     * Defaults to the configuration class's SimpleName followed by the long option, if present. If no long option
     * is set then the field name will be used.
     * <br/><br/>
     * For example, for a Configuration bean option:
     * <pre>
     *     public Class BasicBean {
     *         &#64;CliOption(
     *          shortOption = "f",
     *          longOption = "foo",
     *          description = "A single boolean value" )
     *          public boolean someBoolean;
     *     }</pre><br/>
     * would generate a default value of "BasicBean.foo".  This Configuration bean option:<br/>
     * <pre>
     *     public Class BasicBean {
     *         &#64;CliOption(
     *          shortOption = "f",
     *          description = "A single boolean value" )
     *          public boolean someBoolean;
     *     }</pre><br/>
     * would generate a default key of "BasicBean.someBoolean".
     */
    String propertyKey() default "";

    /**
     * A failure to specify an option that is marked as required will result in a runtime error.
     */
    boolean required() default false;

    /**
     * A comment for the CommandLineOption. Displayed to the right of the flags.
     * Properties file providers that offer property file generation should output this description as a comment on
     * the line above the property.
     */
    String description() default "";

    /**
     * Specify details about the value of the input for the field, such as if it is a list of values, maximums and
     * minimums, etc. The default CliOptionArgument is an instance of the annotation that uses the
     * NullPointerException class as its typeConverter; this is simply a hacky way to allow users of
     * this API to avoid having to specify a value for cases that don't need any extra detail.
     */
    CliOptionArgument argument() default @CliOptionArgument(typeConverter = NullPointerException.class);

}
