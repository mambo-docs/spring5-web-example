package com.example.demo;

import com.example.demo.config.AppConfig;
import com.example.demo.config.WebConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@ContextHierarchy({
    @ContextConfiguration(classes = {AppConfig.class}),
    @ContextConfiguration(classes = {WebConfig.class})
})
@WebAppConfiguration
public class SpringApplicationTests {

    @Autowired
    protected WebApplicationContext wac;
    protected MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void contextLoads() {
        System.out.println(mockMvc);
    }
}
