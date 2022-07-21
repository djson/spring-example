package com.sample.swagger.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.fasterxml.classmate.TypeResolver;
import com.sample.swagger.response.ApiErr;
import com.sample.swagger.response.ApiNoAuth;
import com.sample.swagger.response.ApiRes;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@EnableWebMvc
@Configuration
public class SwaggerConfig extends WebMvcConfigurationSupport {

    @Bean
    public Docket api(TypeResolver typeResolver) {
        return new Docket(DocumentationType.OAS_30)
                // * 실제 에러 처리로 리턴하는 클래스를 명시하고자 할 때 해당 모델을 추가해준다.
                .additionalModels(
                        typeResolver.resolve(ApiRes.class),
                        typeResolver.resolve(ApiErr.class),
                        typeResolver.resolve(ApiNoAuth.class))
                // * 스키마 멤버 변수 중, Date 관련 변수 문제로 인해 설정 추가
                .directModelSubstitute(LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(LocalDateTime.class, java.util.Date.class)
                .useDefaultResponseMessages(true)
                .apiInfo(apiInfo())
                // 인증 토큰 방식이 있을때만 사용.
                // .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sample.swagger.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger 3.0 Api Sample")
                .description("This is Sample")
                .version("1.0")
                .build();
    }

    // 인증 토큰 방식이 있을때만 사용.
    /*
     * private SecurityContext securityContext() {
     * return SecurityContext.builder()
     * .securityReferences(defaultAuth())
     * .build();
     * }
     */

    // 인증 토큰 방식이 있을때만 사용.
    /*
     * private List<SecurityReference> defaultAuth() {
     * AuthorizationScope authorizationScope = new AuthorizationScope("global",
     * "accessEverything");
     * AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
     * authorizationScopes[0] = authorizationScope;
     * return Arrays.asList(new SecurityReference("Authorization",
     * authorizationScopes));
     * }
     */

    private ApiKey apiKey() {
        return new ApiKey("apiKey", "apiKey", "header");
    }
}