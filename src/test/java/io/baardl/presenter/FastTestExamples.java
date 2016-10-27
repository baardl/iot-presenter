package io.baardl.presenter;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Show gropuping functionallity for TestNG
 */
public class FastTestExamples {



    @Test(groups = { "fast"})
    public void testWithGroup() throws Exception {
       assertTrue(true);

    }

    @Test
    public void testMe() throws Exception {
        assertTrue(true);

    }
}
