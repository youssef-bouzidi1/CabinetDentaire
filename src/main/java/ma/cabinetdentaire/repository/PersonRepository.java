package ma.cabinetdentaire.repository;

import ma.cabinetdentaire.entity.Personne;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Personne, Long> {
    @Override
    List<Personne> findAll();
}
