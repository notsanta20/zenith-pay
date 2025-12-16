package com.santa.auth_service.config.jwt;

import com.santa.auth_service.config.MyUserDetailsService;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private ApplicationContext context;
    private final HandlerExceptionResolver resolver;

    private static final List<String> EXCLUDE_URLS =
            Arrays.asList("/api/auth/register", "/api/auth/login");

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    public JwtFilter(ApplicationContext context, JwtService jwtService, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.context = context;
        this.jwtService = jwtService;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!shouldNotFilter(request)){
            try {
                String token = Arrays.stream(request.getCookies())
                        .filter(c -> c.getName().equals("authToken"))
                        .findFirst()
                        .map(Cookie::getValue)
                        .orElse(null);

                String email = null;

                if (token != null) {
                    email = jwtService.extractEmail(token);
                }

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(email);
                    System.out.println(userDetails);

                    if (jwtService.validateToken(token)) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }

                filterChain.doFilter(request, response);
            } catch (Exception e) {
                if (e instanceof NullPointerException) {
                    resolver.resolveException(request, response, null, new SignatureException("JWT missing"));
                } else {
                    resolver.resolveException(request, response, null, e);
                }
            }
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return EXCLUDE_URLS.stream()
                .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
    }
}
