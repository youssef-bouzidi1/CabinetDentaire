package ma.cabinetdentaire.repository;

import ma.cabinetdentaire.entity.Consultation;
import ma.cabinetdentaire.entity.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation,Long> {
}
