package com.thistech.simulator.bean;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yanyang.xie@gmail.com on 22/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ManifestContentTest {
    @Autowired
    private ManifestContent mc;

    @Test
    public void fetchMSFromCache() throws Exception {
        String expectedResult="demo-content";
        String result=mc.fetchMSFromCache("demo.m3u8");
        Assert.assertTrue("Same", result.contains(expectedResult));
        Assert.assertFalse("Different", !result.contains(expectedResult));

    }

}