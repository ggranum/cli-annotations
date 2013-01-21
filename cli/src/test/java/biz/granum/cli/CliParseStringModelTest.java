/*
 * Copyright 2012 Geoff M. Granum,All Rights Reserved.
 *
 * 11/3/12 6:25 PM 
 * @author ggranum
 */
package biz.granum.cli;

import biz.granum.cli.configuration.StringConfigurations;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public abstract class CliParseStringModelTest {

    public abstract <T> CliModelProcessor<String[], T> getProcessor(Class<T> model,
            String header,
            String footer);

    public <T> T getPopulatedModel(String[] args, Class<T> model) throws Exception {
        CliModelProcessor<String[], T> processor = getProcessor(model, "", "");
        return processor.processInput(args);
    }

    @Test
    public void testSimpleDefaultIsEmptyString() throws Exception {
        String[] args = {};
        StringConfigurations model = getPopulatedModel(args, StringConfigurations.class);
        assertThat(model.aStringValue, nullValue());
    }

    @Test
    public void testSimpleValue_ShortOpt_Equals() throws Exception {
        String[] args = {
                "-a=foo"
        };
        StringConfigurations model = getPopulatedModel(args, StringConfigurations.class);
        assertThat(model.aStringValue, is("foo"));
    }

    @Test
    public void testSimpleValue_LongOpt_Equals() throws Exception {
        String[] args = {
                "-aStringValue=foo"
        };
        StringConfigurations model = getPopulatedModel(args, StringConfigurations.class);
        assertThat(model.aStringValue, is("foo"));
    }

    @Test
    public void testListDefaultValues() throws Exception {
        String[] args = {};
        StringConfigurations model = getPopulatedModel(args, StringConfigurations.class);
        assertThat(model.cStringValues.size(), is(3));
        assertThat(model.cStringValues.get(0), is("foo"));
        assertThat(model.cStringValues.get(1), is("bar,baz"));
        assertThat(model.cStringValues.get(2), is("qux"));
    }

}
 
