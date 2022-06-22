package com.example.myrestfulservice;

import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.*;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.service.Contact;

import java.util.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final Contact DEFAULT_CONTACT = new Contact("Kenneth Lee",
            "https://www.joneconsulting.co.kr", "edowon@joneocnsulting.co.kr");
    private static final ApiInfo DEFAULT_API_INFO = new ApiInfo("Awesome API Title",
            "Awesome API Documentation",
            "1.0",
            "urn:tos",
            DEFAULT_CONTACT,
            "Apache 2.0",
            "https://www.apache.org/licenses/LICENSE-2.0",
            new ArrayList());
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(Arrays.asList(
            "application/json", "application/xml"
    ));


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                ;
    }
}
