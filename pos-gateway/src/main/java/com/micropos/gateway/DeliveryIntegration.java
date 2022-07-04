package com.micropos.gateway;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.webflux.dsl.WebFlux;
import org.springframework.stereotype.Component;


@Configuration
public class DeliveryIntegration {
    @Bean
    public DirectChannel deliveryRequestChannel() {
        return new DirectChannel();
    }
}

@Component
class HttpInboundGateway {
    @Bean
    public IntegrationFlow inGate() {
        return IntegrationFlows
                .from(WebFlux.inboundGateway("/integration/deliver")
                )
                .headerFilter("accept-encoding", false)
                .channel("deliveryRequestChannel")
                .get();
    }
}

@Component
class HttpOutboundGateway {

    private final String DELIVER_URL = "http://localhost:45902/deliver/";

    @Bean
    public IntegrationFlow outGate() {
        return IntegrationFlows.from("deliveryRequestChannel")
        .handle(Http.outboundGateway(DELIVER_URL)
                .httpMethod(HttpMethod.GET)
                .expectedResponseType(List.class))
        // .handle(System.out::println)  // void is nothing...
        .get();
    }
}
