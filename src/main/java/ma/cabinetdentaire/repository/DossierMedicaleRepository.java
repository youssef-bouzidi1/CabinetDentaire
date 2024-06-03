package ma.cabinetdentaire.repository;

import ma.cabinetdentaire.entity.DossierMedicale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DossierMedicaleRepository extends JpaRepository<DossierMedicale, Long> {
}
