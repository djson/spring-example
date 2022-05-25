package com.springcloud.webclient.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {
    private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(getClass());

    @Bean
    public WebClient webClient() {

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

        return WebClient.builder()
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
                // 메모리 전략 수립
                .exchangeStrategies(exchangeStrategies)
                // default Header 추가
                .defaultHeader("user-agent",
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.87 Safari/537.3")
                // default Cookie 추가
                .defaultCookie("httpclient-type", "webclient")
                .build();
    }
}
