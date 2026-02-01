package com.hospital.pharmacy.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        Claims claims = jwtUtil.extractAllClaims(token);
        String username = claims.getSubject();

        List<String> roles = claims.get("roles", List.class);

        // DO NOT ADD PREFIX — token already has ROLE_
        var authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        System.out.println("JWT USER = " + username);
        System.out.println("JWT ROLES = " + authorities);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        authorities
                );

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
