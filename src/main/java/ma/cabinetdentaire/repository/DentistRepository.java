package ma.cabinetdentaire.repository;

import ma.cabinetdentaire.entity.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DentistRepository extends JpaRepository<Dentist,Long> {
    @Override
    List<Dentist> findAll();
}
