package controller;

import model.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import repository.PersonRepository;
import service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.SequenceGeneratorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persons")
@CrossOrigin(origins = "http://localhost:4200")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/all")
    public List<Person> getAllPersons() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable String id) {
        Optional<Person> person = personService.findById(id);
        return person.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/insertPerson")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        System.out.println(person.getName());
        try {
            long generatedId = sequenceGeneratorService.generateSequence(Person.SEQUENCE_NAME);
            person.setId(Long.toString(generatedId));
            Person savedPerson = personRepository.save(person);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(savedPerson);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable String id, @RequestBody Person personDetails) {
        Optional<Person> optionalPerson = personService.findById(id);

        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            person.setSaldoInicial(personDetails.getSaldoInicial());
            person.setEstiloDeAposta(personDetails.getEstiloDeAposta());
            return ResponseEntity.ok(personService.save(person));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable String id) {
        personService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}