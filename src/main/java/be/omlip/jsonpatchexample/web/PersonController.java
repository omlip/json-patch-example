package be.omlip.jsonpatchexample.web;

import be.omlip.jsonpatchexample.model.Person;
import be.omlip.jsonpatchexample.dao.PersonRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.json.JsonPatch;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private JsonPatchMapper<Person> mapper;

    private PersonRepository personRepository;

    public PersonController(JsonPatchMapper<Person> mapper, PersonRepository personRepository) {
        this.mapper = mapper;
        this.personRepository = personRepository;
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Person> patchPerson(@PathVariable Integer id, @RequestBody JsonPatch patchDocument) {

        // Find the model that will be patched
        Person person = personRepository.findOne(id);

        // Apply the patch
        Person personPatched = mapper.apply(person, patchDocument);

        // Save the persons patched
        Person savedPerson = personRepository.save(personPatched);

        // Return 200 to indicate the request has succeeded
        return ResponseEntity.ok(
                savedPerson
        );
    }

}
