package be.omlip.jsonpatchexample.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;

@Data
@NoArgsConstructor
public class Person {

    private int id;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private List<Address> addresses;

    public Person(int id, String firstName, String lastName, Date dateOfBirth, Address ... addresses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.addresses = asList(addresses);
    }
}
