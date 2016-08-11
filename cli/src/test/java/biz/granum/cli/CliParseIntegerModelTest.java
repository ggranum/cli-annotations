
package biz.granum.cli;

import biz.granum.cli.configuration.IntegerConfigurations;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public abstract class CliParseIntegerModelTest {

    public abstract <T> CliModelProcessor<String[], T> getProcessor(Class<T> model,
            String header,
            String footer);

    public <T> T getPopulatedModel(String[] args, Class<T> model) throws Exception {
        CliModelProcessor<String[], T> processor = getProcessor(model, "", "");
        return processor.processInput(args);
    }

    @Test
    public void testSimpleDefaultIsZero() throws Exception {
        String[] args = {
        };
        IntegerConfigurations model = getPopulatedModel(args, IntegerConfigurations.class);
        assertThat(model.aIntValue, is(0));
    }

    @Test
    public void testSimpleValue_ShortOpt_Equals() throws Exception {
        String[] args = {
                "-a=10"
        };
        IntegerConfigurations model = getPopulatedModel(args, IntegerConfigurations.class);
        assertThat(model.aIntValue, is(10));
    }

    @Test
    public void testSimpleValue_LongOpt_Equals() throws Exception {
        String[] args = {
                "-aIntValue=10"
        };
        IntegerConfigurations model = getPopulatedModel(args, IntegerConfigurations.class);
        assertThat(model.aIntValue, is(10));
    }

    @Test
    public void testListDefaultValues() throws Exception {
        String[] args = {};
        IntegerConfigurations model = getPopulatedModel(args, IntegerConfigurations.class);
        assertThat(model.cIntValues.size(), is(3));
        assertThat(model.cIntValues.get(0), is(10));
        assertThat(model.cIntValues.get(1), is(20));
        assertThat(model.cIntValues.get(2), is(30));
    }

}
 
