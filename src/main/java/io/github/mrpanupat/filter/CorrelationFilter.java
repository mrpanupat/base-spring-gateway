package io.github.mrpanupat.filter;

import io.github.mrpanupat.util.CorrelationUtil;
import io.micrometer.common.util.StringUtils;
import io.micrometer.tracing.Baggage;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CorrelationFilter implements GlobalFilter, Ordered {

    private final Tracer tracer;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Baggage baggage = tracer.getBaggage("X-Correlation-Id");
        if (baggage != null && StringUtils.isBlank(baggage.get()))
            baggage.set(CorrelationUtil.createCorrelationId());

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -2;
    }
}
