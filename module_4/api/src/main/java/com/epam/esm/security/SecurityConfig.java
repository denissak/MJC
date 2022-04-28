package com.epam.esm.security;

import com.epam.esm.filter.AuthenticationFilter;
import com.epam.esm.filter.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManagerBean());
        authenticationFilter.setFilterProcessesUrl("/signin");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/login", "token/refresh", "/registration", "/register", "/signin", "/css/**", "/img/**", "/js/**", "/mainpage", "/certificate/**").permitAll();
        http.authorizeRequests().antMatchers(GET, "/mainpage", "/user/**", "/role/**", "/cart/**", "/certificates/**", "/certificate/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(GET, "/tag/**", "/certificate/**", "/certificates/**", "/cart/**", "/orders/**").hasAnyAuthority("ADMIN", "USER");
        http.authorizeRequests().antMatchers(POST,   "/orders/**").hasAuthority("USER");
        http.authorizeRequests().antMatchers(POST, "/user/**", "/tag/**", "/certificates/**", "/orders/**", "/role/**", "/auto/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(DELETE, "/user/**", "/tag/**", "/certificates/**", "/orders/**", "/role/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(PUT, "/certificates/**").hasAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(authenticationFilter);
        http.addFilterBefore(new AuthorizationFilter(userDetailsService), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
