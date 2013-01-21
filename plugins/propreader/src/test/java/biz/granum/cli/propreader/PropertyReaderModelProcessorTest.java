/*
 * Copyright 2013 Geoff M. Granum,All Rights Reserved.
 *
 * 1/21/13 10:05 AM 
 * @author ggranum
 */
package biz.granum.cli.propreader;

import java.util.Properties;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class PropertyReaderModelProcessorTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testAddResourcePath() throws Exception {

    }

    @Test
    public void testAddInputSource() throws Exception {

    }

    @Test
    public void testProcessInputForOneResourcePath() throws Exception {
        PropertyReaderModelProcessor<ProcessorTestConfig> processor =
                new PropertyReaderModelProcessor<ProcessorTestConfig>(ProcessorTestConfig.class, "", "");

        Properties p = processor.addResourcePath("PropertyReaderModelProcessorTest_A.properties", getClass());
        assertThat(p, Matchers.notNullValue());
        ProcessorTestConfig config = processor.processInput();
        assertThat(config, Matchers.notNullValue());
        assertThat(config.booleanValue, Matchers.is(true));
        assertThat(config.integerValue, Matchers.is(100));
        assertThat(config.integerList, Matchers.hasItems(30, 40, 50));
        assertThat(config.floatValue, Matchers.is(10.10f));
        assertThat(config.stringValue, Matchers.is("foo'tacular"));

        /* Not set in Test_A: */
        assertThat(config.floatWithDefault, Matchers.is(30.0f));
        assertThat(config.integerListWithDefaults, Matchers.hasItems(10, 20, 30));
        assertThat(config.stringListWithDefaults, Matchers.hasItems("foo", "bar,baz", "qux"));
    }

    @Test
    public void testProcessInputForTwoResourcePathsAdditive() throws Exception {
        PropertyReaderModelProcessor<ProcessorTestConfig> processor =
                new PropertyReaderModelProcessor<ProcessorTestConfig>(ProcessorTestConfig.class, "", "");

        Properties p = processor.addResourcePath("PropertyReaderModelProcessorTest_A.properties", getClass());
        assertThat(p, Matchers.notNullValue());
        p = processor.addResourcePath("PropertyReaderModelProcessorTest_B.properties", getClass());
        ProcessorTestConfig config = processor.processInput();

        /* Not set in Test_B: */
        assertThat(config, Matchers.notNullValue());
        assertThat(config.booleanValue, Matchers.is(true));
        assertThat(config.integerValue, Matchers.is(100));
        assertThat(config.integerList, Matchers.hasItems(30, 40, 50));
        assertThat(config.floatValue, Matchers.is(10.10f));
        assertThat(config.stringValue, Matchers.is("foo'tacular"));

        /* Not set in Test_A but set in Test_B: */
        assertThat(config.floatWithDefault, Matchers.is(101.11f));
        assertThat(config.integerListWithDefaults, Matchers.hasItems(300, 400, -500));
        assertThat(config.stringListWithDefaults, Matchers.hasItems("qux", "foo", "baz"));
    }

    @Test
    public void testProcessInputForThreeResourcePathsWithOverrides() throws Exception {
        PropertyReaderModelProcessor<ProcessorTestConfig> processor =
                new PropertyReaderModelProcessor<ProcessorTestConfig>(ProcessorTestConfig.class);

        Properties p = processor.addResourcePath("PropertyReaderModelProcessorTest_A.properties", getClass());
        assertThat(p, Matchers.notNullValue());
        p = processor.addResourcePath("PropertyReaderModelProcessorTest_B.properties", getClass());
        assertThat(p, Matchers.notNullValue());
        p = processor.addResourcePath("PropertyReaderModelProcessorTest_C.properties", getClass());
        assertThat(p, Matchers.notNullValue());

        ProcessorTestConfig config = processor.processInput();
        assertThat(config, Matchers.notNullValue());

        /* Not set in Test_A but set in Test_B: */
        assertThat(config.floatWithDefault, Matchers.is(101.11f));
        assertThat(config.stringListWithDefaults, Matchers.hasItems("qux", "foo", "baz"));

        /* Overridden in Test_C: */
        assertThat(config.booleanValue, Matchers.is(false));
        assertThat(config.integerValue, Matchers.is(1000));
        assertThat(config.integerList, Matchers.hasItems(3330, 4440, -5550));
        assertThat(config.floatValue, Matchers.is(1110.10f));
        assertThat(config.stringValue, Matchers.is("qux'tacular"));
        assertThat(config.integerListWithDefaults, Matchers.hasItems(3000, 4000, -5000));

    }
}
 
