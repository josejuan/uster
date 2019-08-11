package com.uster.server.rest;

import com.uster.model.License;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {"com.uster"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ControllerTest {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void licenses() throws Exception {

        final List<License> result = rest.exchange(
                "/uster/licenses",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<License>>() {
                }
        ).getBody();

        assertTrue(result != null && result.size() > 2, "license number must be greater than 2");

    }

    @Test
    void update() {
    }

    @Test
    void insert() {
    }

    @Test
    void remove() {
    }

    @Test
    void lookup() {
    }

    @Test
    void vehicles() {
    }
}