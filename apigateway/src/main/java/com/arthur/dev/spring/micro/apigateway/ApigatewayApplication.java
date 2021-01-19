package com.arthur.dev.spring.micro.apigateway;

import com.arthur.dev.spring.micro.apigateway.filter.AuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.*;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
@EnableEurekaClient
public class ApigatewayApplication {

	@Value("${server.servlet.context-path}")
	private String contextPath;

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder, AuthenticationFilter authenticationFilter){
		String userServiceRegex = contextPath +"\\/users\\/(?<segment>.*)";
		String userServiceReplace = "/${segment}";

		return builder.routes()
				.route(p -> p
						.path(contextPath + "/users/**")
						.filters( f -> f.rewritePath(userServiceRegex, userServiceReplace).filter(authenticationFilter.))
						.uri("lb://USERS/"))
				.route( p -> p.path(contextPath + "/login").uri("lb://AUTH/"))
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayApplication.class, args);
	}

}
