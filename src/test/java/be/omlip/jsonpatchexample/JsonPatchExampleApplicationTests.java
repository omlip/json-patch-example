package be.omlip.jsonpatchexample;

import be.omlip.jsonpatchexample.dao.PersonRepository;
import be.omlip.jsonpatchexample.model.Address;
import be.omlip.jsonpatchexample.model.Person;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JsonPatchExampleApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    public void resetData() {
        personRepository.getPersons().clear();
        personRepository.getPersons().add(
                new Person(1, "John", "Doe", new Date(),
                        new Address(0, "Beers street", 8, "4000","Liège","Belgium")
                )
        );

    }

    @Test
    public void testReplaceFirstName() {

        String patchPayload = "[ { \"op\": \"replace\", \"path\": \"/firstName\", \"value\": \"Oliver\" }]";

        JsonNode response = restTemplate.patchForObject("/persons/1", patchPayload, JsonNode.class);

        assertNotNull(response);
        assertEquals("Oliver", response.path("firstName").asText());
    }

    @Test
    public void testCopyLastName() {

        String patchPayload = "[ { \"op\": \"copy\", \"from\": \"/firstName\", \"path\": \"/lastName\" }]";

        JsonNode response = restTemplate.patchForObject("/persons/1", patchPayload, JsonNode.class);

        assertNotNull(response);
        assertEquals(response.path("firstName").asText(), response.path("lastName").asText());
    }

    @Test
    public void testTestLastNameEqualsDoe() {
        String patchPayload = "[ { \"op\": \"test\", \"path\": \"/lastName\", \"value\": \"Doe\" }]";

        JsonNode response = restTemplate.patchForObject("/persons/1", patchPayload, JsonNode.class);
        assertNotNull(response);
    }

    @Test
    public void testRemoveLastName() {
        String patchPayload = "[ { \"op\": \"remove\", \"path\": \"/lastName\"}]";

        JsonNode response = restTemplate.patchForObject("/persons/1", patchPayload, JsonNode.class);
        assertNotNull(response);
        assertEquals(JsonNodeType.NULL, response.path("lastName").getNodeType());
    }

    @Test
    public void testReplaceAddressCity() {
        String patchPayload = "[ { \"op\": \"replace\", \"path\": \"/addresses/0/city\", \"value\": \"New-York\" }]";

        JsonNode response = restTemplate.patchForObject("/persons/1", patchPayload, JsonNode.class);

        assertNotNull(response);

        assertEquals("New-York", response.path("addresses").get(0).path("city").asText());
    }


    @TestConfiguration
    static class TestRestTemplateJsonPatchConfiguration {

        private static final String APPLICATION_JSON_PATCH = "application/json-patch+json";

        @Bean
        public RestTemplateBuilder restTemplateBuilder() {

            // To make PATCH request with TestRestTemplate, see https://github.com/spring-projects/spring-framework/issues/19618
            return new RestTemplateBuilder()
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_PATCH)
                    .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                    ;
        }
    }

}
