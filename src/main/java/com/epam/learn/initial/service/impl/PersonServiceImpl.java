package com.epam.learn.initial.service.impl;

import com.epam.learn.initial.repository.PersonRepository;
import com.epam.learn.initial.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;
}
