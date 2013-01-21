/*
 * Copyright 2012 Geoff M. Granum,All Rights Reserved.
 *
 * 11/3/12 6:25 PM 
 * @author ggranum
 */
package biz.granum.cli.plugintest;

import biz.granum.cli.annotation.CliOption;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public abstract class ModelValidationTest<I> extends PluginTest<I> {

    @Test()
    public void testEmptyAnnotation() throws Exception {
        I args = getTestData().getSimpleBoolean(BooleanWithOnlyPropertyKey.class, "boolFlag", true);
        BooleanWithOnlyPropertyKey model = getPopulatedModel(args, BooleanWithOnlyPropertyKey.class);
        assertThat(model.boolFlag, is(true));
    }

    public static class BooleanWithOnlyPropertyKey {
        public BooleanWithOnlyPropertyKey() {
        }

        @CliOption
        public Boolean boolFlag;
    }
}
 
