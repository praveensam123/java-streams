package com.rps.employee.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.lang.String.format;

@Getter
@Setter
@NoArgsConstructor
public class Employee {

    private String firstName;
    private String lastName;
    private String initial;
    private Gender gender;
    private String dateOfBirth;
    private String dateOfJoining;
    private String grade;
    private int salary;

    @Override
    public String toString() {
        return format("%s %s %s", firstName, lastName, initial);
    }

    public Name toName() {
        return new Name(firstName, lastName, initial);
    }

}
