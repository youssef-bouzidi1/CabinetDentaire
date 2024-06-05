package ma.cabinetdentaire.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateConsultation;

    @ManyToOne
    private DossierMedicale dossierMedicale;

    @Enumerated(EnumType.STRING)
    private TypeConsultation typeConsultation= TypeConsultation.TEST;

    @OneToMany(mappedBy = "consultation")
    private List<InterventionMedicale> interventionMedicales;
    @OneToMany(mappedBy = "consultation")
    private List<Facture> factures;

    // Getters and Setters
}

