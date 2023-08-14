package com.testAutomationFramework.dummy;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DummyTest {
    @Test
    public void testFail(){
        Assert.fail("test fail");
    }
}
