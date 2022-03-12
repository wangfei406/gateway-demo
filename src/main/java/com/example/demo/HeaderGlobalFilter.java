package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import wiremock.net.minidev.json.JSONArray;

import java.util.Collections;
import java.util.UUID;

@Component
public class HeaderGlobalFilter implements GlobalFilter, Ordered {
    static Logger logger = LoggerFactory.getLogger(HeaderGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("request = {}", JSONArray.toJSONString(Collections.singletonList(exchange.getRequest())));

        //add header X-Request-ID1 if not exist
        String headerName = "X-Request-ID1";
        String requestId = exchange.getRequest().getHeaders().getFirst(headerName);
        if (StringUtils.isEmpty(requestId)) {
            ServerHttpRequest host = exchange.getRequest().mutate().header(headerName, UUID.randomUUID().toString()).build();
            ServerWebExchange build = exchange.mutate().request(host).build();
            return chain.filter(build);
        } else {
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
