package io.github.mrpanupat.util;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class CorrelationUtil {

    public static String createCorrelationId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
