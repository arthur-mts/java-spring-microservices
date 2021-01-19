package com.arthur.dev.spring.micro.auth.security.filter;

import com.arthur.dev.spring.micro.auth.dto.LoginDTO;
import com.arthur.dev.spring.micro.core.property.JwtConfig;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

@Slf4j
public class JWTEmailAndPassAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private JwtConfig jwtConfig;

    public JWTEmailAndPassAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        setFilterProcessesUrl(this.jwtConfig.getLoginUrl());
        setUsernameParameter("email");
        setPasswordParameter("password");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        Authentication authentication = attemptAuthentication((HttpServletRequest) request, (HttpServletResponse) response);
        successfulAuthentication((HttpServletRequest)request,(HttpServletResponse) response, chain, authentication);
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginDTO credentials;

        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);
        }
        catch (IOException ex) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing password or email");
        }

        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword(), Collections.emptyList());

        Authentication authentication = authenticationManager.  authenticate(usernamePasswordAuthenticationToken);

        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = JWT.create().withSubject(
                ((UserDetails) authResult.getPrincipal()).getUsername())
                .withExpiresAt(
                        new Date(System.currentTimeMillis()
                                + jwtConfig.getExpiration()))
                .sign(Algorithm.HMAC256(jwtConfig.getSecretKey().getBytes()));

        response.getWriter().write(token);
        response.getWriter().flush();
    }

}
