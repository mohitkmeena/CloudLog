package com.mohit.scag.securityconfig;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mohit.scag.service.JwtService;
import com.mohit.scag.service.UserDataService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE+1)
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
  @Autowired
  private ApplicationContext context;
   @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                String authHeader=request.getHeader("Authorization");
               
                String token=null;
                String username=null;
                if(authHeader!=null && authHeader.startsWith("Bearer")) {
                    //token start from 7
                    token=authHeader.substring(7);
                    
                    username=jwtService.extractUsername(token);
                }
                if(username!=null && SecurityContextHolder.getContext().getAuthentication()!=null){
                    UserDetails userDetails=context.getBean(UserDataService.class).loadUserByUsername(username);
                    if(jwtService.validateToken(token,userDetails)) {
                        UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        
                    }
                }
                filterChain.doFilter(request, response);

    }
    
}
