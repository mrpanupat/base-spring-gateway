package io.github.mrpanupat.config;

import io.github.mrpanupat.util.ContextPropagationUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.support.ipresolver.XForwardedRemoteAddressResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;

@Log4j2
@Configuration
public class RateLimitConfig {

    @Bean
    public KeyResolver customKeyResolver() {
        return exchange -> {
            //Get user IP
            XForwardedRemoteAddressResolver resolver = XForwardedRemoteAddressResolver.maxTrustedIndex(1);
            InetSocketAddress inetSocketAddress = resolver.resolve(exchange);
            return ContextPropagationUtil.observeCtx(() -> {
                String ipAddress = inetSocketAddress.getAddress().getHostAddress();
                log.info("Rate limit ip {}", ipAddress);
                return Mono.just(ipAddress);
            });
        };
    }

}

