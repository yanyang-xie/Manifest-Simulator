package com.thistech.simulator.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.List;

@Configuration
@PropertySources({@PropertySource(value = "file:conf/ms.properties"),})
public class MSProperties {
    @Value("${response.url.redundancy}")
    private String responseUrlRedundancy;

    @Value("${content.seed.suffix}")
    private String contentSeedSuffix;

    @Value("${response.delay.time.min:0}")
    private int responseMinDelayTime;

    @Value("${response.delay.time.max:0}")
    private int responseMaxDelayTime;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public String getResponseUrlRedundancy() {
        return responseUrlRedundancy;
    }

    public String getContentSeedSuffix() {
        return contentSeedSuffix;
    }

    public int getResponseMaxDelayTime() {
        return responseMaxDelayTime;
    }

    public int getResponseMinDelayTime() {
        return responseMinDelayTime;
    }

}