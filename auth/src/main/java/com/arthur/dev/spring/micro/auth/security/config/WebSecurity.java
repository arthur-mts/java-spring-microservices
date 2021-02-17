package com.arthur.dev.spring.micro.auth.security.config;

import com.arthur.dev.spring.micro.auth.helpers.UserDetailsServiceImpl;
import com.arthur.dev.spring.micro.auth.security.filter.JWTEmailAndPassAuthenticationFilter;
import com.arthur.dev.spring.micro.auth.security.filter.JWTEmailAndPassAuthorizationFilter;
import com.arthur.dev.spring.micro.core.property.JwtConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Slf4j
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();

        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, jwtConfig.getLoginUrl())
                .permitAll()
                .and()
                .addFilter(
                        new JWTEmailAndPassAuthenticationFilter
                            (authenticationManager(), jwtConfig));
    }
    

}
