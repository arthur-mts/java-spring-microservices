package com.arthur.dev.spring.micro.apigateway;

import com.arthur.dev.spring.micro.apigateway.filter.UserAuthenticationFilterFactory;
import com.arthur.dev.spring.micro.core.property.JwtConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.*;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties(value = JwtConfig.class)
public class ApigatewayApplication {

	@Value("${server.servlet.context-path}")
	private String contextPath;

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder, UserAuthenticationFilterFactory userAuthenticationFilterFactory){
		String userServiceRegex = contextPath +"\\/users\\/(?<segment>.*)";
		String userServiceReplace = "/${segment}";
		log.info("HERE");
		log.info(contextPath);
		return builder.routes()
				.route(p -> p
						.path(contextPath + "/users/**")
						.filters( f -> f.rewritePath(userServiceRegex, userServiceReplace).filter(userAuthenticationFilterFactory.apply(new UserAuthenticationFilterFactory.Config(true))))
						.uri("lb://USERS/"))
				.route( p -> p.path(contextPath + "/login").uri("lb://AUTH/"))
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayApplication.class, args);
	}

}
