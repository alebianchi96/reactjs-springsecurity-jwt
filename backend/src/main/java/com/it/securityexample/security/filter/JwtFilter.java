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

    private static final String JWT_REQUEST_HEADER = "Authorization";
    private static final String JWT_REQUEST_PREFIX = "Bearer ";

    private static final String JWT_RESPONSE_HEADER = "XX_AUTH_JWT_TOKEN";

    /**
     * Method to lazily fetch the UserService bean from the ApplicationContext
     * This is done to avoid Circular Dependency issues
     * 
     * @return
     */
    private UserService getUserService() {
        return applicationContext.getBean(UserService.class);
    }

    /**
     * 1. Get the JWT from request.header "Authorization"
     * 2. Get username from JWT
     * 3. Get UserDetails by username extracted from the token
     * 4. Validate the token by UserDetails
     * 5. if Token JWT is valid -> Check user and password
     * 6. if All is ok -> the SecurityContext can be updated by current token
     * 7. if All is ok -> A refreshed token can be created and passed back to client
     * for further requests
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Get the JWT from Request.Header
        String authHeader = request.getHeader(JWT_REQUEST_HEADER);
        if (authHeader == null || !authHeader.startsWith(JWT_REQUEST_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring(7);

        // Get username from JWT
        String userName = jwtService.extractUserName(token);
        if (userName == null
                || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        boolean isValid = false;
        UserDetails userDetails = null;
        try {
            // Get UserDetails by username extracted from the token
            userDetails = getUserService().loadUserByUsername(userName);
            // Validate the token by UserDetails
            isValid = jwtService.validateToken(token, userDetails);
        } catch (ExpiredJwtException e) {
            log.error("Expired token");
            // a generic 403 exception will be returned caused by other prioritized filter
            // wrapping
        }

        if (isValid) {

            // Token JWT is valid so Check user and password
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // All is ok so the SecurityContext can be updated by current token
            SecurityContextHolder.getContext().setAuthentication(authToken);

            // A refreshed token can be created and passed back to client for
            // further requests
            String refreshedToken = jwtService.generateToken(userName);
            response.addHeader(JWT_RESPONSE_HEADER, refreshedToken);

        }

        filterChain.doFilter(request, response);

    }
}
