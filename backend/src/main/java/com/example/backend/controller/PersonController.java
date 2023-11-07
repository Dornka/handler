package com.example.backend.controller;


import com.example.backend.model.Address;
import com.example.backend.model.NewPerson;
import com.example.backend.model.Person;
import com.example.backend.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable String id){return personService.getPerson(id);}

    @PutMapping("{id}")
    public Person updatePerson(@PathVariable String id, @RequestBody Person person){
        if (!id.equals(person.id())){
            throw new IllegalArgumentException("Person ID not found or not matched");
        }
        return personService.updatePerson(person);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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
