package com.arthur.dev.spring.micro.core.property;

import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt.config")
@Getter
@ToString
public class JwtConfig {
    private String loginUrl = "/login/**";

    private long expiration = 900_000;

    private String secretKey = "mysecret";

    private String type = "encrypted";

    private String prefix = "Bearer ";

    private String headerString = "Authorization";


}
