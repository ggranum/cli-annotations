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
    @Override
    public Properties getSimpleStringList() {
        Properties p = new Properties();
        p.put("simpleStringList", "foo,bar,baz");
        return p;
    }

    @Override
    public Properties getSimpleFlag() {
        Properties p = new Properties();
        p.put("simpleFlag", "true");
        return p;
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
}
 
