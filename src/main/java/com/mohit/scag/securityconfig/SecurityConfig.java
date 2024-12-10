package com.mohit.scag.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
     @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); 
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(encoder);
         return authenticationProvider;    
    }
    @Autowired
    private ThreatDetectionFilter threatDetectionFilter;
    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {
        
        http.csrf(customizer->customizer.disable())
            .httpBasic(Customizer.withDefaults())
            .authorizeHttpRequests(request->request
            .requestMatchers("/admin/**").hasRole("ADMIN") 
            .requestMatchers("/user/**").hasRole("USER") 
            .requestMatchers("/login").permitAll()
            .requestMatchers("/register").permitAll()
            .anyRequest().authenticated())
            .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(threatDetectionFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
            
        return  http.build();    
    }

    @Bean
    public UserDetailsService userDetailsService() {
               UserDetails user=User.withDefaultPasswordEncoder().username("root").password("root").roles("USER","ADMIN").build();

        return new InMemoryUserDetailsManager(user) ;
    }
    @Bean
    public AuthenticationManager authmanager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
    
}
