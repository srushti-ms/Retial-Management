package com.example.retail_management.config;


import com.example.retail_management.services.UserDetailsServiceClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration //tell spring that this is a configuration class
@EnableWebSecurity  //dont use customised filterchain, go asper mentioned below
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // disable CSRF (not needed for stateless REST APIs)
                .csrf(csrf -> csrf.disable())


                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/signin").permitAll()  // allow login & signup
                        .anyRequest().authenticated()                      // all others require auth
                )

                // use basic auth if needed (you'll replace with JWT filter later)
                .httpBasic(Customizer.withDefaults())

                // make session stateless (because JWT is stateless)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }


    @Bean // this is an authentication provider, which authenticated the unauthenticated requests
    public AuthenticationProvider authenticationProvider(UserDetailsServiceClass userDetailsServiceClass){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        provider.setUserDetailsService(userDetailsServiceClass);

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       AuthenticationProvider authenticationProvider) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider)
                .build();
    }



}
