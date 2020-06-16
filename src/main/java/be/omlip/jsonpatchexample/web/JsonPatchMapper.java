package be.omlip.jsonpatchexample.web;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.json.JsonPatch;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import java.util.function.BiFunction;

public class JsonPatchMapper<T> implements BiFunction<T, JsonPatch, T> {

    private ObjectMapper objectMapper;

    public JsonPatchMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public T apply(T targetBean, JsonPatch patch) {
        // Convert the Java bean to a JSON document
        JsonStructure target = objectMapper.convertValue(targetBean, JsonStructure.class);

        // Apply the JSON Patch to the JSON document
        JsonValue patched = patch.apply(target);

        // Convert the JSON document to a Java bean and return it
        return objectMapper.convertValue(patched, (Class<T>)targetBean.getClass());
    }

}
