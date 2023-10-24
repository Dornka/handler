package com.example.backend.controller;


import com.example.backend.model.Address;
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
        if (!newPerson.firstName().isEmpty() && !newPerson.lastName().isEmpty()){
            Person result = personService.save(newPerson);
            return ResponseEntity.ok(result);
        }   else{
            return ResponseEntity.badRequest().body("Bitte alles ausfüllen.");
        }
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable String id) {personService.delete(id);}
}
