package com.springcloud.webclient;

import reactor.netty.http.client.HttpClient;
import java.time.Duration;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import reactor.core.publisher.Mono;

@RestController
public class WebClientController {

    private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(getClass());

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

    @GetMapping("/test2")
    public Mono<String> doTest2() {

        HttpClient httpClient = HttpClient.create()
                // keepalive option
                .option(ChannelOption.SO_KEEPALIVE, true)
                // 연결 활성화 유지 시간
                .option(EpollChannelOption.TCP_KEEPIDLE, 300)
                // 연결 활성화 유효성 검사 주기
                .option(EpollChannelOption.TCP_KEEPINTVL, 60)
                // 연결 종료 전 검사 횟수
                .option(EpollChannelOption.TCP_KEEPCNT, 8)
                // 연결 대기하는 시간
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
                // 응답 대기하는 시간
                .responseTimeout(Duration.ofSeconds(30));

        WebClient client = WebClient.builder()
                .baseUrl("http://localhost:5011")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                // filter 를 이용한 Request / Response Control
                .filter(
                        (req, next) -> next.exchange(
                                // 예시 작업 : request 헤더 추가
                                ClientRequest.from(req).header("from", "webclient").build()))
                .filter(
                        ExchangeFilterFunction.ofRequestProcessor(
                                // 예시 작업 : request 헤더 로깅
                                clientRequest -> {
                                    log.info(">>>>>>>>>> REQUEST <<<<<<<<<<");
                                    log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
                                    clientRequest.headers().forEach(
                                            (name, values) -> values
                                                    .forEach(value -> log.info("{} : {}", name, value)));
                                    return Mono.just(clientRequest);
                                }))
                .filter(
                        // 예시 작업 : response 헤더 로깅
                        ExchangeFilterFunction.ofResponseProcessor(
                                clientResponse -> {
                                    log.info(">>>>>>>>>> RESPONSE <<<<<<<<<<");
                                    clientResponse.headers().asHttpHeaders().forEach((name, values) -> values
                                            .forEach(value -> log.info("{} : {}", name, value)));
                                    return Mono.just(clientResponse);
                                }))
                .build();

        return client.get().uri("/webclient/test-builder").retrieve().bodyToMono(String.class);

    }
}
