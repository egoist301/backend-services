package com.epam.learn.initial.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.epam.learn.initial.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @Test
    public void givenPersonRepository_whenSaveAndRetreivePerson_thenOK() {
        Person person = personRepository.save(new Person());
        Person foundPerson = personRepository.findById(person.getId()).get();
        assertNotNull(foundPerson);
        assertEquals(person.getValue(), foundPerson.getValue());
    }
}
