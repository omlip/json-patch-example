package be.omlip.jsonpatchexample;

import be.omlip.jsonpatchexample.model.Person;
import be.omlip.jsonpatchexample.web.JsonMergePatchMapper;
import be.omlip.jsonpatchexample.web.JsonPatchMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr353.JSR353Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JsonPatchExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsonPatchExampleApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR353Module());
        return mapper;
    }

    @Bean
    public JsonPatchMapper<Person> personJsonPatchMapper(ObjectMapper mapper) {
        return new JsonPatchMapper<>(mapper);
    }

    @Bean
    public JsonMergePatchMapper<Person> personJsonMergePatchMapper(ObjectMapper mapper) {
        return new JsonMergePatchMapper<>(mapper);
    }

}
