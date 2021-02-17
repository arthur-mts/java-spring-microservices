package com.arthur.dev.spring.micro.apigateway.filter;

import com.arthur.dev.spring.micro.core.property.JwtConfig;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Slf4j
@Component
public class UserAuthenticationFilterFactory extends AbstractGatewayFilterFactory<UserAuthenticationFilterFactory.Config> {

    @Autowired
    private JwtConfig jwtConfig;

    public UserAuthenticationFilterFactory() {
        super(Config.class);
    }


    private Optional<String> generateToken(ServerHttpRequest request) {
        String token = request.getHeaders().get(jwtConfig.getHeaderString()).get(0).split(" ")[1];

        String user;

        try {
            log.info(JWT.decode(token).getPayload());
            user = JWT.require(Algorithm.HMAC256(jwtConfig.getSecretKey().getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();
        }
        catch (JWTVerificationException e) {
            return Optional.empty();
        }

        if(user != null){
            return Optional.of(user);
        }
        return Optional.empty();
    }


    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) ->{
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            String path = request.getPath().value();


            if(Arrays.asList(path.split("/")).contains("signin")) {
                return chain.filter(exchange);
            }


            boolean headerContainsToken = request.getHeaders().containsKey(jwtConfig.getHeaderString());

            if(!headerContainsToken){
                return unauthorizeRequest(response);
            }

            Optional<String> token = generateToken(request);

            if(token.isPresent()) {
                log.info("GET TOKEN WITH SUCCES");
                return chain.filter(exchange);
            }
            return unauthorizeRequest(response);
        });
    }

    private Mono<Void> unauthorizeRequest(ServerHttpResponse response) {
        log.info("UNAUTHORIZED");
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    @AllArgsConstructor
    public static class Config {
        private boolean isPrivate;
    }
}
