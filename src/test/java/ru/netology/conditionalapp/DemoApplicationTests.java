package ru.netology.conditionalapp;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    @Container
    private static GenericContainer<?> devContainer = new GenericContainer<>("devapp")
            .withExposedPorts(8080);

    @Container
    private static GenericContainer<?> prodContainer = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devContainer.start();
        prodContainer.start();
    }

    @Test
    void testDevApp() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" + devContainer.getMappedPort(8080), String.class);
        assertEquals("ExpectedDevResponse", forEntity.getBody());
    }

    @Test
    void testProdApp() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" + prodContainer.getMappedPort(8081), String.class);
        assertEquals("ExpectedProdResponse", forEntity.getBody());
    }

}