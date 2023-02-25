package com.example.apigatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {
    // 커스텀 필터와 다르게 모든 라우트에 공통적으로 적용되는 필터이다.
    public GlobalFilter(){
        super(GlobalFilter.Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) ->{
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("글로벌 필터 기본 메세지 = {}",config.getBaseMessage());
            if(config.isPreLogger()){
                log.info("글로벌 필터 시작: request id = {}", request.getId());
            }
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                if(config.isPostLogger()){
                    log.info("글로벌 필터 끝: response code = {}", response.getStatusCode());
                }
            }));
        });
    }

    @Data
    public static class Config{
        // 컨피규레이션 프로퍼티를 넣을 수 있다.
        // application yaml 파일에서 가져올 것이다.
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
