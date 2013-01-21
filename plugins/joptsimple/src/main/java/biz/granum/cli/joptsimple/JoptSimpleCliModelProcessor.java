/*
 * Copyright 2013 Geoff M. Granum,All Rights Reserved.
 *
 * 1/19/13 4:04 PM 
 * @author ggranum
 */
package biz.granum.cli.joptsimple;

import biz.granum.cli.CliModelProcessor;

public class JoptSimpleCliModelProcessor<T> extends CliModelProcessor<String[], T> {

    public JoptSimpleCliModelProcessor(Class<T> modelClass,
            String helpHeader, String helpFooter) {
        super(modelClass, new JoptSimpleCliProvider(), helpHeader, helpFooter);
    }
}
 
