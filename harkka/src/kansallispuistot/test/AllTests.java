package kansallispuistot.test;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import kanta.test.TietueTest;

/**
 * @author vilikelo
 * @version 10 Nov 2022
 *
 */
@Suite
@SelectClasses({ ReittiTest.class,
                 ReititTest.class,
                 KansallispuistotTest.class,
                 KommentitTest.class,
                 KommenttiTest.class,
                 TietueTest.class
                })
public class AllTests {
    //
}
