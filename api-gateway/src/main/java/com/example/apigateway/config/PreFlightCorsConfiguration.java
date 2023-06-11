package com.example.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
public class PreFlightCorsConfiguration {


    private static final String ALLOWED_HEADERS = "x-requested-with,authorization," + "refreshtoken,Access-Control-Allow-Origin,Content-Type," + "credential,X-AUTH-TOKEN,X-CSRF-TOKEN";


    private static final String ALLOWED_METHODS = "GET, PUT, POST, DELETE, OPTIONS";


    private static final String ALLOWED_ORIGIN = "*";
    private static final String ALLOWED_CREDENTIALS = "true";

    // 중요 해당 헤더가 없다면 axios를 통한 프론트에서 확인이 불가능함
    private static final String EXPOSE_HEADERS = "*,Authorization,Refreshtoken," + "authorization,refreshtoken";


    private static final String MAX_AGE = "3600";


    @Bean
    public WebFilter corsFilter() {


        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            if (CorsUtils.isPreFlightRequest(request)) {

                ServerHttpResponse response = ctx.getResponse();

                HttpHeaders headers = response.getHeaders();

                headers.add("Access-Control-Allow-Origin", ALLOWED_ORIGIN);

                headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);

                headers.add("Access-Control-Max-Age", MAX_AGE);

                headers.add("Access-Control-Allow-Headers", ALLOWED_HEADERS);


                headers.add("Access-Control-Allow-Credentials", ALLOWED_CREDENTIALS);


                // 프론트에서 해당 헤더를 확인할수잇게 허가해줌

                headers.add("Access-Control-Expose-Headers", EXPOSE_HEADERS);


                if (request.getMethod() == HttpMethod.OPTIONS) {

                    response.setStatusCode(HttpStatus.OK);

                    return Mono.empty();


                }


            }


            return chain.filter(ctx);


        };


    }


}
