package com.caden.fitnessapp.fullStacked.filter;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.caden.fitnessapp.fullStacked.service.CustomUserDetailsService;
import com.caden.fitnessapp.fullStacked.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        try{
            if(authHeader != null && authHeader.startsWith("Bearer ")){
                token = authHeader.substring(7);
                username = jwtService.extractUserName(token);
            }

            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){

                UserDetails userDetails = context.getBean(CustomUserDetailsService.class).loadUserByUsername(username);

                if(jwtService.validateToken(token,userDetails)){
                    UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }catch(Exception e){
            System.err.println("JWT Filter error: " + e.getMessage());
        }

        System.out.println("Auth header: " + authHeader);
        System.out.println("Token: " + token);
        System.out.println("Username: " + username);
        System.out.println("Authentication: " + SecurityContextHolder.getContext().getAuthentication());
        filterChain.doFilter(request, response);
    }
}
