package com.example.backend.service;


import com.example.backend.model.Address;
import com.example.backend.model.NewPerson;
import com.example.backend.model.Person;
import com.example.backend.repository.PersonRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PersonService {

    private final PersonRepo personRepo;

    public PersonService(PersonRepo personRepo) {this.personRepo = personRepo;}

    public List<Person> getAll (){
        return personRepo.findAll();
    }

    public Person getPerson(String id){
        return personRepo.findById(id).orElseThrow();
    }

    public Person save(NewPerson newPerson){
        Address saveAddress = new Address(newPerson.address().addressPLZ(), newPerson.address().addressCity(), newPerson.address().addressStreet(), newPerson.address().addressHouseNumber());
        Person savePerson = new Person(UUID.randomUUID().toString(), newPerson.firstName(), newPerson.lastName(), saveAddress);
        return personRepo.save(savePerson);
    }

    public void delete(String id) { personRepo.deleteById(id);}

    public Person updatePerson(Person person){

        Person updatedPerson = new Person(
                person.id(),
                person.firstName(),
                person.lastName(),
                person.address()
        );

        return personRepo.save(updatedPerson);
    }
}
