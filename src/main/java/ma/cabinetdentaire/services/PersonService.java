package ma.cabinetdentaire.services;

import ma.cabinetdentaire.entity.Personne;
import ma.cabinetdentaire.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    public List<Personne> findAll(){
        return personRepository.findAll();
    }
    public Optional<Personne> findById(Long id) {return personRepository.findById(id);}
    public Personne save(Personne personne) {
        return personRepository.save(personne);
    }

    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }
}
