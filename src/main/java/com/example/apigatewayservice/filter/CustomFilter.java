package com.example.apigatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    public CustomFilter(){
        super(Config.class);
    }

    public static class Config{
        // 컨피규레이션 프로퍼티를 넣을 수 있다.
    }

    @Override
    public GatewayFilter apply(Config config) {
        // 커스텀 pre 필터로 JWT토큰 추출과 인증을 관리할 수 있다.
        return ((exchange, chain) -> {
            // 네티라는 비동기 서버를 사용할 때는 아래의 리퀘스트와 리스폰스를 사용한다.
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("커스텀 pre 필터: request uri = {}", request.getId());

            // 커스텀 post 필터로 에러 리스폰스 핸들러를 에러 코드에 따라 부를 수 있다.
            // 아래 리턴 객체로 등록이 가능하다.
            // 모노는 스프링 5에서 지원되는 웹플럭스 기술로 비동기 방식으로 단일값을 전달 가능하다.
            return chain.filter(exchange).then(Mono.fromRunnable(()-> {
                log.info("커스텀 post 필터: response code = {}", response.getStatusCode());
            }));
        });
    }

}
