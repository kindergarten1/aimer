package com.cilcil.login.config;



import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author SlyAimer
 * @Date 2023/8/28 18:46
 * @Version 1.0
 */

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("呲哩呲哩")
                        .description("呲哩呲哩 后台")
                        .version("V1")
                        .contact(new Contact().name("Aimer").email("2289782085@qq.com").url("http://www.baidu.com"))
                        .termsOfService("用于测试呲哩呲哩开发的接口")
                );
    }


}
