package com.jorge.jpa.springboot_jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PersonDto {

    private String name;
    private String lastname;


    @Override
    public String toString() {
        return "PersonDto{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
