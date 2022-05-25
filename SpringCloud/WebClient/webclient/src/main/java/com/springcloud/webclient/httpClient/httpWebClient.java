package com.springcloud.webclient.httpClient;

import java.nio.charset.Charset;
import java.time.Duration;
import java.util.HashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

public class httpWebClient {
        private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(getClass());

        public static Mono<xml> conn(String url, String uri, HashMap<String, String> parameter) {

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

                // Encoding Mode
                DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(url);
                factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

                WebClient client = WebClient.builder()
                                .uriBuilderFactory(factory)
                                .baseUrl(url)
                                .clientConnector(new ReactorClientHttpConnector(httpClient))
                                // filter 를 이용한 Request / Response Control
                                /*
                                 * .filter(
                                 * (req, next) -> next.exchange(
                                 * // 예시 작업 : request 헤더 추가
                                 * ClientRequest.from(req).header("from", "webclient")
                                 * .build()))
                                 * .filter(
                                 * ExchangeFilterFunction.ofRequestProcessor(
                                 * // 예시 작업 : request 헤더 로깅
                                 * clientRequest -> {
                                 * log.info(">>>>>>>>>> REQUEST <<<<<<<<<<");
                                 * log.info("Request: {} {}",
                                 * clientRequest.method(),
                                 * clientRequest.url());
                                 * clientRequest.headers().forEach(
                                 * (name, values) -> values
                                 * .forEach(value -> log
                                 * .info("{} : {}", name,
                                 * value)));
                                 * return Mono.just(clientRequest);
                                 * }))
                                 * .filter(
                                 * // 예시 작업 : response 헤더 로깅
                                 * ExchangeFilterFunction.ofResponseProcessor(
                                 * clientResponse -> {
                                 * log.info(">>>>>>>>>> RESPONSE <<<<<<<<<<");
                                 * clientResponse.headers().asHttpHeaders()
                                 * .forEach((name, values) -> values
                                 * .forEach(value -> log
                                 * .info("{} : {}", name,
                                 * value)));
                                 * return Mono.just(clientResponse);
                                 * }))
                                 */
                                // 메모리 전략 수립
                                .exchangeStrategies(exchangeStrategies)
                                // default Header 추가
                                .defaultHeader("user-agent",
                                                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.87 Safari/537.3")
                                // default Cookie 추가
                                .defaultCookie("httpclient-type", "webclient")
                                .build();

                Mono<xml> response = client.get().uri(
                                uriBuilder -> uriBuilder.path(uri)
                                                .queryParam("ServiceKey", parameter.get("ServiceKey"))
                                                .queryParam("pageNo", parameter.get("pageNo"))
                                                .queryParam("numOfRows", parameter.get("numOfRows"))
                                                .queryParam("solYear", parameter.get("solYear"))
                                                .queryParam("solMonth", parameter.get("solMonth"))
                                                .build())
                                .accept(MediaType.TEXT_XML)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML_VALUE)
                                .acceptCharset(Charset.forName("UTF-8"))
                                .exchangeToMono(x -> x.bodyToMono(xml.class));
                System.out.println("res : " + response);
                response.doOnNext(x -> System.out.println(">> x : " + x));
                return response;
        }
}

// zUvO72slCykSeT84dIr2RNZ6%252BSgtcDses4UJlMaYyvAI9Gk6ncwTcvB4WQgvfjOjJb6KSOMhExBSZQ6xsR%252FDcg%253D%253D
// zUvO72slCykSeT84dIr2RNZ6%2BSgtcDses4UJlMaYyvAI9Gk6ncwTcvB4WQgvfjOjJb6KSOMhExBSZQ6xsR%2FDcg%3D%3D
// zUvO72slCykSeT84dIr2RNZ6%2BSgtcDses4UJlMaYyvAI9Gk6ncwTcvB4WQgvfjOjJb6KSOMhExBSZQ6xsR%2FDcg%3D%3D
// zUvO72slCykSeT84dIr2RNZ6%2BSgtcDses4UJlMaYyvAI9Gk6ncwTcvB4WQgvfjOjJb6KSOMhExBSZQ6xsR%2FDcg%3D%3D