package ma.cabinetdentaire.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class InterventionMedicale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String noteMedecin;
    private Double prixPatient;
    private Long dent;
    @ManyToOne
    private Consultation consultation;

    @ManyToOne
    private Acte acte;

    // Getters and Setters
}

