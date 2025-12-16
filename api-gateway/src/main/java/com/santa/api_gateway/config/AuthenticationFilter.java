package com.santa.api_gateway.config;

import com.santa.api_gateway.config.jwt.JwtUtil;
import com.santa.api_gateway.exception.JwtMissingException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouteValidator validator;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticationFilter(RouteValidator validator, JwtUtil jwtUtil) {
        super(Config.class);
        this.validator = validator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            String email = null;
            String userId = null;

            if (validator.isSecured.test(exchange.getRequest())) {
                String token;

                if (exchange.getRequest().getCookies().containsKey("authToken")) {
                    token = exchange.getRequest().getCookies().get("authToken").getFirst().getValue();
                } else {
                    throw new JwtMissingException();
                }


                jwtUtil.validateToken(token);

                Claims claims = jwtUtil.extractAllClaims(token);
                email = jwtUtil.extractEmail(token);
                userId = claims.get("userId").toString();
            }

            ServerHttpRequest mutatedReq = exchange.getRequest()
                            .mutate()
                                    .header("userEmail", email)
                                            .header("userId", userId)
                                                    .build();


            ServerWebExchange mutatedExchange = exchange.mutate()
                    .request(mutatedReq)
                    .build();

            return chain.filter(mutatedExchange);
        });
    }

    public static class Config {

    }
}
