package ma.cabinetdentaire.services;

import ma.cabinetdentaire.entity.Dentist;
import ma.cabinetdentaire.repository.DentistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DentistService {
    @Autowired
    private DentistRepository dentistRepository;
    public List<Dentist> findAll(){return dentistRepository.findAll();}
    public Optional<Dentist> findById(Long id){return dentistRepository.findById(id);}
    public Dentist save(Dentist dentist){return dentistRepository.save(dentist);}
    public void deleteById(Long id){dentistRepository.deleteById(id);}
}
