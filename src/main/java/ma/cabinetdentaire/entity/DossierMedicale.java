package ma.cabinetdentaire.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class DossierMedicale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateCreation;

    @ManyToOne
    private Dentist medecinTraitant;
    private String numDossier;

    @OneToOne
    private Patient patient;

    @OneToMany(mappedBy = "dossierMedicale")
    private List<Consultation> consultations;

    @OneToMany(mappedBy = "dossierMedicale")
    private List<SituationFinanciere> situationFinancieres;
    private StatusPaiement statusPaiement;

}

