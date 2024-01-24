package com.core.config;

import com.core.service.ServiceImpl.ValidationServiceImpl;
import com.core.service.ValidationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "com/core")
@EnableAspectJAutoProxy
public class ApplicationContextConfig {

}
