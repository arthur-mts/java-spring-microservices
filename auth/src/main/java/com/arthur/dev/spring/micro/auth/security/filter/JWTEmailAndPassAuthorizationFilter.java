package com.arthur.dev.spring.micro.auth.security.filter;

import com.arthur.dev.spring.micro.core.property.JwtConfig;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Slf4j
public class JWTEmailAndPassAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtConfig jwtConfig;

    public JWTEmailAndPassAuthorizationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig) {
        super(authenticationManager);
        this.jwtConfig = jwtConfig;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(jwtConfig.getHeaderString());
        log.info("ATTEMP TO GET TOKEn");
        if(header == null || ! header.startsWith(jwtConfig.getPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

         SecurityContextHolder.getContext().setAuthentication(authentication);


         chain.doFilter(request, response);

    }

    public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String header = request.getHeader(jwtConfig.getHeaderString());
        
        if(header != null){
            String user = JWT.require(Algorithm.HMAC256(jwtConfig.getSecretKey().getBytes()))
                    .build()
                    .verify(header.replace(jwtConfig.getPrefix(), ""))
                    .getSubject();

            if(user != null){
                return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
            }
        }
        return null;
    }
}
