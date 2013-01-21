/*
 * Copyright 2013 Geoff M. Granum,All Rights Reserved.
 *
 * 1/20/13 4:00 PM 
 * @author ggranum
 */
package biz.granum.cli.byproperties;

import biz.granum.cli.TestData;
import java.util.Properties;

public class PropertyFileTestDataImpl extends TestData<Properties> {

    private PropertiesTestCase getCase(String title, String msg, String... pairs) {
        return new PropertiesTestCase(title, msg, getProps(pairs));
    }

    private Properties getProps(String... pairs) {
        Properties props = new Properties();

        for (int i = 0; i < pairs.length; i += 2) {
            props.put(pairs[i], pairs[i + 1]);
        }

        return props;

    }

    @Override
    public Properties getSimpleBoolean(Class modelClass, String fieldName, boolean value) {
        return getProps(modelClass.getSimpleName() + "." + fieldName, String.valueOf(value));
    }

    @Override
    public Properties getSimpleStringList() {
        return getProps("simpleStringList", "foo,bar,baz");
    }

    @Override
    public PropertiesTestCase[] getFlagCases() {
        return new PropertiesTestCase[]{getCase("", "", "booleanPropertyKey", "true")};
    }

    @Override
    public Properties getEmptyInput() {
        return new Properties();
    }

    @Override
    public Properties getTestUnknownArgument() {
        Properties p = new Properties();
        p.put("aiksjdfkjasdf", "foo,bar,baz");
        return p;
    }

    @Override
    public Properties getShowHelpFlag() {
        Properties p = new Properties();
        p.put("showHelp", "true");
        return p;

    }

    @Override
    public PropertiesTestCase[] getSetBooleanToTrueCases() {
        return new PropertiesTestCase[]{
                getCase("By explicit value",
                        "booleanPropertyKey=true", "booleanPropertyKey", "true")
        };
    }

    @Override
    public PropertiesTestCase[] getSetBooleanToFalseCases() {
        return new PropertiesTestCase[]{
                getCase("By explicit value",
                        "booleanPropertyKey=false", "booleanPropertyKey", "false"),
                getCase("By absent value",
                        "booleanPropertyKey=", "booleanPropertyKey", ""),
                getCase("By bad input that happens to parse correctly.",
                        "booleanPropertyKey=random", "booleanPropertyKey", "random"),
                getCase("By bad input that happens to parse correctly.",
                        "booleanPropertyKey=1", "booleanPropertyKey", "1")
        };
    }

}
 
