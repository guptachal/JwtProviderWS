package com.microservice.loginWS.config.mapperConfig;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperBean {
    @Bean
    public ModelMapper mapper(){return new ModelMapper();}
}
