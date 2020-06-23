package be.omlip.jsonpatchexample;

import be.omlip.jsonpatchexample.dao.PersonRepository;
import be.omlip.jsonpatchexample.model.Address;
import be.omlip.jsonpatchexample.model.Person;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JsonMergePatchTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    public void prepareData() {

        List<Person> persons = new ArrayList<>();

        persons.add(
                new Person(1, "John", "Doe", new Date(), new Address(0, "Beers street", 8, "4000", "Liège", "Belgium"))
        );

        personRepository.setPersons(persons);
    }

    @AfterEach
    public void clearData() {
        personRepository.setPersons(null);
    }


    @Test
    public void testJsonMergePatch() {
        String patchPayload = "{ \"firstName\" : \"Oliver\" }";

        restTemplate.patchForObject("/persons/1", patchPayload, Void.class);

        JsonNode response = restTemplate.getForObject("/persons/1", JsonNode.class);
        assertEquals("Oliver", response.path("firstName").asText());
    }

    @TestConfiguration
    static class TestRestTemplateJsonPatchConfiguration {

        private static final String APPLICATION_JSON_MERGE_PATCH = "application/merge-patch+json";

        @Bean
        public RestTemplateBuilder restTemplateBuilder() {

            // To make PATCH request with TestRestTemplate, see https://github.com/spring-projects/spring-framework/issues/19618
            return new RestTemplateBuilder()
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_MERGE_PATCH)
                    .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                    ;
        }
    }


}
