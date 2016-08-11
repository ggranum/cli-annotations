
package biz.granum.cli.byproperties;

import biz.granum.cli.CliModelProcessor;
import biz.granum.cli.configuration.StringConfigurations;
import java.util.Properties;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public abstract class ModelFromPropertiesStringTest {

    public abstract <T> CliModelProcessor<Properties, T> getProcessor(Class<T> model,
            String header,
            String footer);

    public <T> T getPopulatedModel(Properties p, Class<T> model) throws Exception {
        CliModelProcessor<Properties, T> processor = getProcessor(model, "", "");
        return processor.processInput(p);
    }

    @Test
    public void testSimpleDefaultIsEmptyString() throws Exception {
        Properties p = new Properties();
        StringConfigurations model = getPopulatedModel(p, StringConfigurations.class);
        assertThat(model.aStringValue, nullValue());
    }

    @Test
    public void testSimpleValue_Equals() throws Exception {
        Properties p = new Properties();
        p.put("StringConfigurations.aStringValue", "foo");
        StringConfigurations model = getPopulatedModel(p, StringConfigurations.class);
        assertThat(model.aStringValue, is("foo"));
    }

    @Test
    public void testListDefaultValues() throws Exception {
        Properties p = new Properties();
        StringConfigurations model = getPopulatedModel(p, StringConfigurations.class);
        assertThat(model.cStringValues.size(), is(3));
        assertThat(model.cStringValues.get(0), is("foo"));
        assertThat(model.cStringValues.get(1), is("bar,baz"));
        assertThat(model.cStringValues.get(2), is("qux"));
    }

}
 
