package io.github.mrpanupat.probe.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.core.Is.is;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
class ProbeControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Should get gateway info success")
    void getInfo_success() {
        webTestClient.get().uri("/info")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.build.name").isNotEmpty()
                .jsonPath("$.build.version").isNotEmpty()
                .jsonPath("$.build.artifact").isNotEmpty();
    }

    @Test
    @DisplayName("Should get gateway health success")
    void getHealthIndicator_success() {
        webTestClient.get().uri("/health")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.status").value(is("UP"));
    }

}
