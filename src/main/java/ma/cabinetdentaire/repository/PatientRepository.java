package ma.cabinetdentaire.repository;

import ma.cabinetdentaire.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    @Override
    List<Patient> findAll();
}
