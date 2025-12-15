package com.santa.api_gateway.config;

import com.santa.api_gateway.config.jwt.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private RouteValidator validator;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthenticationFilter(RouteValidator validator, JwtUtil jwtUtil) {
        super(Config.class);
        this.validator = validator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                String token;

                if(exchange.getRequest().getCookies().containsKey("authToken")) {
                    token = exchange.getRequest().getCookies().get("authToken").getFirst().getValue();
                }
                else{
                    throw new JwtException("missing auth token");
                }


                jwtUtil.validateToken(token);
            }
            return chain.filter(exchange);
        });
    }

    public static class Config{

    }
}
