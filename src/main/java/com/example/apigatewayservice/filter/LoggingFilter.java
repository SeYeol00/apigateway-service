package com.example.apigatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter(){
        super(LoggingFilter.Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
//        return ((exchange, chain) ->{
//            ServerHttpRequest request = exchange.getRequest();
//            ServerHttpResponse response = exchange.getResponse();
//
//            log.info("로깅 필터 기본 메세지 = {}",config.getBaseMessage());
//            if(config.isPreLogger()){
//                log.info("로깅 pre 필터 시작: request id = {}", request.getId());
//            }
//            return chain.filter(exchange).then(Mono.fromRunnable(()->{
//                if(config.isPostLogger()){
//                    log.info("로깅 post 필터 끝: response code = {}", response.getStatusCode());
//                }
//            }));
//        });
        // 필터를 정의하는 것이다.
        // 일종의 커스텀 필터의 역할
        GatewayFilter filter = new OrderedGatewayFilter((exchange,chain)->{
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("로깅 필터 기본 메세지 = {}",config.getBaseMessage());
            if(config.isPreLogger()){
                log.info("로깅 pre 필터 시작: request id = {}", request.getId());
            }
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                if(config.isPostLogger()){
                    log.info("로깅 post 필터 끝: response code = {}", response.getStatusCode());
                }
            }));
            // 필터 우선순위
        }, Ordered.LOWEST_PRECEDENCE);
        return filter;
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
