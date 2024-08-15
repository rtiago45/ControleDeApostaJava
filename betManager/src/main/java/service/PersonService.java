package service;

import model.Person;
import repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(String id) {
        return personRepository.findById(id);
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public void deleteById(String id) {
        personRepository.deleteById(id);
    }

    public Person updateSaldoAtual(String personId, double winnings) {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            person.setSaldoAtual(person.getSaldoAtual() + winnings);
            return personRepository.save(person);
        } else {
            throw new RuntimeException("Person not found");
        }
    }

}
