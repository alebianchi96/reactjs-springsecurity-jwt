package com.it.securityexample.security.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.it.securityexample.security.JwtService;
import com.it.securityexample.user.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ApplicationContext applicationContext;

    // Method to lazily fetch the UserService bean from the ApplicationContext
    // This is done to avoid Circular Dependency issues
    private UserService getUserService() {
        return applicationContext.getBean(UserService.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extracting the token from the Authorization header
        String token = authHeader.substring(7);
        String userName = jwtService.extractUserName(token);

        if (userName == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        // If username is extracted and there is no authentication in the current
        // SecurityContext

        // Loading UserDetails by username extracted from the token
        boolean isValid = false;
        UserDetails userDetails = null;
        try {
            userDetails = getUserService().loadUserByUsername(userName);
            isValid = jwtService.validateToken(token, userDetails);
        } catch (ExpiredJwtException e) {
            log.error("Expired token");
            // a generic 403 exception will be returned caused by other prioritized filter
            // wrapping
        }

        // Validating the token with loaded UserDetails
        if (isValid) {

            // Creating an authentication token using UserDetails
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            // Setting authentication details
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // Setting the authentication token in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authToken);

        }

        // Proceeding with the filter chain
        filterChain.doFilter(request, response);

    }
}
