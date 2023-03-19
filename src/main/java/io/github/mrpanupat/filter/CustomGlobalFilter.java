package io.github.mrpanupat.filter;

import io.github.mrpanupat.util.ContextPropagationUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("before custom global filter");
        return chain.filter(exchange).then(ContextPropagationUtil.observeCtx(() -> {
            log.info("afet custom global filter");
            return Mono.empty();
        }));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}