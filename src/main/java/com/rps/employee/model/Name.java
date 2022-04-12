package com.rps.employee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Name {

    private String firstName;
    private String lastName;
    private String initial;

    @Override
    public String toString() {
        return String.format("%s %s %s", firstName, lastName, initial);
    }

}
