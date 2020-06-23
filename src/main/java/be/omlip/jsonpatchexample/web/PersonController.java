package be.omlip.jsonpatchexample.web;

import be.omlip.jsonpatchexample.dao.PersonRepository;
import be.omlip.jsonpatchexample.model.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.json.JsonMergePatch;
import javax.json.JsonPatch;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private JsonPatchMapper<Person> mapper;
    private JsonMergePatchMapper<Person> mergeMapper;

    private PersonRepository personRepository;

    public PersonController(JsonPatchMapper<Person> mapper, JsonMergePatchMapper<Person> mergeMapper, PersonRepository personRepository) {
        this.mapper = mapper;
        this.mergeMapper = mergeMapper;
        this.personRepository = personRepository;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Integer id) {
        return ResponseEntity.ok().body(
                personRepository.findOne(id)
        );
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Person> patchPerson(@PathVariable Integer id, @RequestBody JsonPatch patchDocument) {

        // Find the model that will be patched
        Person person = personRepository.findOne(id);

        // Apply the patch
        Person personPatched = mapper.apply(person, patchDocument);

        // Save the persons patched
        personRepository.save(personPatched);

        // Return 204 to indicate the request has succeeded
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Person> updatePerson(@PathVariable Integer id,
                                              @RequestBody JsonMergePatch patchDocument) {

        // Find the model that will be patched
        Person person = personRepository.findOne(id);

        // Apply the patch
        Person personPatched = mergeMapper.apply(person, patchDocument);

        // Persist the changes
        personRepository.save(personPatched);

        // Return 204 to indicate the request has succeeded
        return ResponseEntity.noContent().build();
    }

}
