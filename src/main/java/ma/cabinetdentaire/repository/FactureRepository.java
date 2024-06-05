package ma.cabinetdentaire.repository;

import ma.cabinetdentaire.entity.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactureRepository extends JpaRepository<Facture, Long> {
}
