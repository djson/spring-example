package com.springcloud.webclient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Duration;
import java.util.HashMap;

import com.springcloud.webclient.httpClient.httpWebClient;
import com.springcloud.webclient.httpClient.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@RestController
public class WebClientController {

        private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(getClass());

        @Autowired
        private WebClient webClient;

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

                // Memory 조정: 2M (default 256KB)
                ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024))
                                .build();

                WebClient client = WebClient.builder()
                                .baseUrl("http://localhost:5011")
                                .clientConnector(new ReactorClientHttpConnector(httpClient))
                                // filter 를 이용한 Request / Response Control
                                .filter(
                                                (req, next) -> next.exchange(
                                                                // 예시 작업 : request 헤더 추가
                                                                ClientRequest.from(req).header("from", "webclient")
                                                                                .build()))
                                .filter(
                                                ExchangeFilterFunction.ofRequestProcessor(
                                                                // 예시 작업 : request 헤더 로깅
                                                                clientRequest -> {
                                                                        log.info(">>>>>>>>>> REQUEST <<<<<<<<<<");
                                                                        log.info("Request: {} {}",
                                                                                        clientRequest.method(),
                                                                                        clientRequest.url());
                                                                        clientRequest.headers().forEach(
                                                                                        (name, values) -> values
                                                                                                        .forEach(value -> log
                                                                                                                        .info("{} : {}", name,
                                                                                                                                        value)));
                                                                        return Mono.just(clientRequest);
                                                                }))
                                .filter(
                                                // 예시 작업 : response 헤더 로깅
                                                ExchangeFilterFunction.ofResponseProcessor(
                                                                clientResponse -> {
                                                                        log.info(">>>>>>>>>> RESPONSE <<<<<<<<<<");
                                                                        clientResponse.headers().asHttpHeaders()
                                                                                        .forEach((name, values) -> values
                                                                                                        .forEach(value -> log
                                                                                                                        .info("{} : {}", name,
                                                                                                                                        value)));
                                                                        return Mono.just(clientResponse);
                                                                }))
                                .exchangeStrategies(
                                                exchangeStrategies)
                                .build();

                return client.get().uri("/webclient/test-builder").retrieve().bodyToMono(String.class);

        }

        @GetMapping("/test3")
        public Mono<String> doTest3() {
                // webConfig 로 이관하여 사용.
                return webClient.get().uri("/webclient/test-builder").retrieve().bodyToMono(String.class);
        }

        @GetMapping("/test4")
        public Mono<String> doTest4() {
                // mutate() 로 baseUrl 변경하여 재빌드 후, 사용가능.
                return webClient.mutate().baseUrl("http://localhost:5011").build().get().uri("/webclient/test-builder")
                                .retrieve().bodyToMono(String.class);
        }

        @GetMapping("/test5")
        public void doTest5() {
                HashMap<String, String> parameter = new HashMap<String, String>();
                try {
                        parameter.put("ServiceKey",
                                        "zUvO72slCykSeT84dIr2RNZ6%2BSgtcDses4UJlMaYyvAI9Gk6ncwTcvB4WQgvfjOjJb6KSOMhExBSZQ6xsR%2FDcg%3D%3D");
                        parameter.put("pageNo", URLEncoder.encode("1", "UTF-8"));
                        parameter.put("numOfRows", URLEncoder.encode("100", "UTF-8"));
                        parameter.put("solYear", URLEncoder.encode("2022", "UTF-8"));
                        parameter.put("solMonth", URLEncoder.encode("06", "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                Mono<xml> xml = httpWebClient.conn("http://apis.data.go.kr",
                                "/B090041/openapi/service/SpcdeInfoService/getRestDeInfo", parameter);
                xml.subscribe(result -> System.out.println(result));

        }
}
