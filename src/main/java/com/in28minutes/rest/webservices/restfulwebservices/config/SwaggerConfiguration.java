package com.in28minutes.rest.webservices.restfulwebservices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    private static final Contact DEFAULT_CONTACT = new Contact("Iryna", "http://localhost:8080", "iryna6794@gmail.com");
    private static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
            "REST FULL API SERVICES",  "Api Documentation", "1.0",
            "urn:tos", DEFAULT_CONTACT.getName() + ", " + DEFAULT_CONTACT.getEmail() + ", " +  DEFAULT_CONTACT.getUrl(),
            "Apache 2.0", "http://www.apache.org/licences/LICENSE-2.0");
    private static final Set<String> DEFAULT_PRODUSES_CONSUMES = new HashSet<>(Arrays.asList("application/json", "application/xml"));

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUSES_CONSUMES)
                .consumes(DEFAULT_PRODUSES_CONSUMES);
    }
}