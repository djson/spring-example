package com.springcloud.webclient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
public class WebClientController {

    /* 가장 기본적인 WebClient 사용 예제 */
    @GetMapping("/test")
    public Mono<String> doTest() {
        WebClient client = WebClient.create();
        /*
         * 빠르게 테스트 시에는 create() 를 사용.
         * 실제는 builder 를 이용하여 옵션 부여하며 사용.
         */
        return client.get()
                .uri("http://localhost:5011/webclient/test-create")
                .retrieve()
                .bodyToMono(String.class);
        /* 1개의 값을 리턴할 때는 BodyToMono, 복수의 값을 리턴할 때는 BodyToFlux 사용 */
    }
}
