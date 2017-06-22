package com.thistech.simulator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.thistech.simulator.bean.MSProperties;
import com.thistech.simulator.bean.ManifestContent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.thistech.simulator.controller.ManifestController;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManifestApplicationTests {

    private MockMvc mvc;

    @Autowired
    private MSProperties msProperties;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // standaloneSetup only for one controller, not for integration web test. so if use it, autowired data could not be initialized.
        //this.mvc = MockMvcBuilders.standaloneSetup(new ManifestController()).build();
    }

    @Test
    public void contextLoads() throws Exception {
        RequestBuilder request = get("/origin/playlists/demo.m3u8");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(msProperties.getResponseUrlRedundancy() + "demo-content"));

        request = get("/wrongURL");
        mvc.perform(request).andExpect(status().isNotFound());
    }

}
