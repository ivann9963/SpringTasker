package com.example.springtasker.security;

import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;

/**
 * JwtAuthenticationFilter must extend OncePerRequestFilter so it can be registered in the Spring Security filter chain.
 * This allows it to process each HTTP request once per request lifecycle.
 *
 * This filter:
 * 1. Checks for a JWT in the Authorization header of each request.
 * 2. Validates the JWT using JwtUtil.
 * 3. If valid, loads the user details and sets authentication in the SecurityContext.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 1. Get the Authorization header from the request
        String authHeader = request.getHeader("Authorization");

        // 2. Check if the header is present and starts with "Bearer "
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            // 3. Extract the token (remove "Bearer ")
            String token = authHeader.substring(7);
            // 4. Validate the token and extract the username
            String username = jwtUtil.extractUsername(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 5. Load user details from the database
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // 6. Validate the token against user details
                if (jwtUtil.validateToken(token, userDetails)) {
                    // 7. Create an authentication token and set it in the context
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        // 8. Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
