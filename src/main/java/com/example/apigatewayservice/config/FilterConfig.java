package com.example.apigatewayservice.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FilterConfig {

    // yaml 파일의 동작을 필터로 지정할 것이다.
//    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                // 빌더를 이용하여 설계한다.
                // 패스로 요청이 오면 filters()의 체이닝 함수를 통해 헤더에 값을 추가하고 보내겠다.
                .route(r -> r.path("/first-service/**")
                            .filters(f -> f.addRequestHeader("first-request","first-request-header")
                                        .addResponseHeader("first-response","first-response-header"))
                        // 아래 주소로 패스를 보내준다.
                            .uri("http://localhost:8081"))

                .route(r -> r.path("/second-service/**")
                        .filters(f -> f.addRequestHeader("second-request","second-request-header")
                                .addResponseHeader("second-response","second-response-header"))
                        // 아래 주소로 패스를 보내준다.
                        .uri("http://localhost:8082"))
                .build();
    }
}
