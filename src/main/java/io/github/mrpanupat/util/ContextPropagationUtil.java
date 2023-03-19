package io.github.mrpanupat.util;

import io.micrometer.context.ContextSnapshot;
import io.micrometer.observation.contextpropagation.ObservationThreadLocalAccessor;
import lombok.experimental.UtilityClass;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;


@UtilityClass
public class ContextPropagationUtil {
    public <T> Mono<T> observeCtx(Supplier<Mono<T>> supplier) {
        return Mono.deferContextual(contextView -> {
            try (ContextSnapshot.Scope ignored = ContextSnapshot.setThreadLocalsFrom(contextView, ObservationThreadLocalAccessor.KEY)) {
                return supplier.get();
            }
        });
    }
}
