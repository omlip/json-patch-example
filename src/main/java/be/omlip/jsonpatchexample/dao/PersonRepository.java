package be.omlip.jsonpatchexample.dao;

import be.omlip.jsonpatchexample.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRepository {

    private List<Person> persons;

    public Person findOne(int id) {
        return persons.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Person with id {} not found", id));
    }

    public Person save(Person patchedPerson) {

        persons.removeIf(person -> person.getId() == patchedPerson.getId());
        persons.add(patchedPerson);

        return findOne(patchedPerson.getId());
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
