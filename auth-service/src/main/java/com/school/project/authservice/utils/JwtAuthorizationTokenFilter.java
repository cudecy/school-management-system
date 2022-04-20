package com.school.project.authservice.utils;

import com.school.project.authservice.services.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {
    private CustomUserDetailsService userDetailsService;

    private JwtTokenUtil jwtTokenUtil;

    public JwtAuthorizationTokenFilter(CustomUserDetailsService customUserDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.userDetailsService = customUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Value("${jwt.auth.header}")
    private String authenticationHeader;

    @Value("${jwt.bearer}")
    private String bearer;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.debug("Processing authentication for '{}'", request.getRequestURL());
        final String requestHeader = request.getHeader(this.authenticationHeader);
        String emailAddress = null;
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith(this.bearer)) {
            authToken = requestHeader.substring(7);
            emailAddress = jwtTokenUtil.getUsernameFromToken(authToken);
        }
        log.debug("checking authentication for '{}'", emailAddress);
        if (emailAddress != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.debug("security context was null, so authorizing user");
            try {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(emailAddress);
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    log.info("authorized user '{}', setting security context", emailAddress);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }catch (Exception ignored) {}
        }
        chain.doFilter(request, response);
    }
}
