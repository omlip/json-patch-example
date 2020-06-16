package be.omlip.jsonpatchexample.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private int id;

    private String street;

    private int number;

    private String zipCode;

    private String city;

    private String country;

}
