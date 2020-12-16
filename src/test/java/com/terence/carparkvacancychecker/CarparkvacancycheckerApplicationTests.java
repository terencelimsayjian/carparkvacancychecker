package com.terence.carparkvacancychecker;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarparkvacancycheckerApplicationIntegrationTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  void contextLoads() {
  }

  @Test
  void shouldHaveActuatorHealthEndpoint() throws Exception {
    ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("/actuator/health", String.class);
    String body = responseEntity.getBody();

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    JSONAssert.assertEquals("{status: UP}", body, JSONCompareMode.STRICT);
  }
}
