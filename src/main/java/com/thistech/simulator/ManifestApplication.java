package com.thistech.simulator;

import com.thistech.simulator.bean.ManifestContent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

@SpringBootApplication
@PropertySource(value = "file:conf/ms.properties", ignoreResourceNotFound = true)
@ComponentScan(basePackages = { "com.thistech.*" })
public class ManifestApplication {
    private static Logger logger = LoggerFactory.getLogger(ManifestApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ManifestApplication.class, args);
    }

    @PostConstruct
    private void init(){
        logger.info("Spring Boot - Manifest Simulator Started.");
        ManifestContent.initResponseFiles();
    }
}
