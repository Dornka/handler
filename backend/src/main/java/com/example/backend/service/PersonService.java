package com.example.backend.service;


import com.example.backend.model.Person;
import com.example.backend.repository.PersonRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepo personRepo;

    public PersonService(PersonRepo personRepo) {this.personRepo = personRepo;}

    public List<Person> getAll (){
        return personRepo.findAll();
    }

    public Person save(Person savePerson){
        return personRepo.save(savePerson);
    }

    public void delete(String id) { personRepo.deleteById(id);}
}
