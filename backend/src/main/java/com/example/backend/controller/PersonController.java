package com.example.backend.controller;


import com.example.backend.model.NewPerson;
import com.example.backend.model.Person;
import com.example.backend.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/persons")
public class PersonController {


    private final PersonService personService;


    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getAllPersons(){
        return personService.getAll();
    }


    @PostMapping
    public ResponseEntity<Object> postPerson(@RequestBody NewPerson newPerson){
        if (!newPerson.firstName().isEmpty() && !newPerson.lastName().isEmpty()&& !newPerson.addressPLZ().isEmpty()&& !newPerson.addressCity().isEmpty()&& !newPerson.addressStreet().isEmpty()&& !newPerson.addressHouseNumber().isEmpty()){
            Person savePerson = new Person(UUID.randomUUID().toString(), newPerson.firstName(),newPerson.lastName(), newPerson.addressPLZ(), newPerson.addressCity(), newPerson.addressStreet(), newPerson.addressHouseNumber());
            return ResponseEntity.ok(personService.save(savePerson));
        }   else{
            return ResponseEntity.badRequest().body("Bitte alles ausfüllen.");
        }
    }

    @DeleteMapping("id")
    void delete(@PathVariable String id) {personService.delete(id);}
}
