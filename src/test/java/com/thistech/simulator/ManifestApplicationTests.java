package com.thistech.simulator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.thistech.simulator.controller.ManifestController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManifestApplicationTests {

	private MockMvc mvc;

	@Before
	public void setup() {
		this.mvc = MockMvcBuilders.standaloneSetup(new ManifestController()).build();
	}

	@Test
	public void contextLoads() throws Exception {
		RequestBuilder request = get("/origin/playlists/demo/demo1/demo.m3u8");
		mvc.perform(request).andExpect(status().isOk()).andExpect(content().string("demo-content"));
		
		request = get("/index");
		mvc.perform(request).andExpect(status().isNotFound());
	}

}
