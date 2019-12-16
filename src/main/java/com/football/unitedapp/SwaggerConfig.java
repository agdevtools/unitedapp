package com.football.unitedapp;

import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SwaggerDefinition(info = @Info(description = "My API",
        version = "V1",title = "The Man United Team App",
        contact = @Contact(name = "Armand Gaillard",
                email = "armand_gaillard@hotmail.com")))


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("The Man United Team App")
                .description("A minimalist rest API app to demonstrate how easy it " +
                        "is to get Sping applications up and running.  ")
                .contact("Armand Gaillard @ armand_gaillard")
                .build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.football.unitedapp"))
                .paths(PathSelectors.any())
                .build();
    }
}
