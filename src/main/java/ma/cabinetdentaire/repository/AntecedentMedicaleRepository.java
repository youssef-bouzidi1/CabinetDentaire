package ma.cabinetdentaire.repository;

import ma.cabinetdentaire.entity.AntecedentMedicale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AntecedentMedicaleRepository extends JpaRepository <AntecedentMedicale,Long> {
    @Override
    List<AntecedentMedicale> findAll();
}
