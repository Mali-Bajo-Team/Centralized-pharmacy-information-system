package com.pharmacy.cpis.config;

import com.pharmacy.cpis.security.TokenUtils;
import com.pharmacy.cpis.security.auth.RestAuthenticationEntryPoint;
import com.pharmacy.cpis.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

// Enable annotation "@Pre*" & "@Post*" which will check authorization for every method access (with that annotation ofc)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // Service which is used for reading data about application users
    @Autowired
    private UserService jwtUserDetailsService;

    // Handler for returning 401, when client with an incorrect username and password tries to access a resource
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    // We register an authentication manager who will do user authentication for us
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // We define instructions for the authentication manager which service to use to retrieve data about the user who wants to authenticate
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService);
    }

    // We define access rights to specific URLs
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Communication between the client and the server is stateless since it is a REST application
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // Process all unauthenticated requests uniformly and send a 401 error
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()

                // Allow all users to access defined paths (/h2-console/** if we are use temporary DB instead postgres )
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/api/users/public").permitAll() // Test some endpoint which is allowed

                // For every other request the user must be authenticated
                .anyRequest().authenticated().and()
                // For development purposes include configuration for CORS from the WebConfig class
                .cors().and();

                // Insert custom filter TokenAuthenticationFilter to check JWT tokens instead of pure username and password (performed by BasicAuthenticationFilter)
//                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService),
//                        BasicAuthenticationFilter.class);
        // Due to the simplicity of the example
        http.csrf().disable();
    }

    // Generalna bezbednost aplikacije
    @Override
    public void configure(WebSecurity web) throws Exception {
        // TokenAuthenticationFilter will ignore everything below the specified path
        web.ignoring().antMatchers(HttpMethod.POST, "/auth/login");
        web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html",
                "/**/*.css", "/**/*.js");
    }

}
