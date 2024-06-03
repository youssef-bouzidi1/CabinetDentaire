package ma.cabinetdentaire.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class SituationFinanciere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double montant;
    private LocalDate datePaiement;

    @ManyToOne
    private DossierMedicale dossierMedicale;

    @ManyToOne
    private Caisse caisse;

    @Enumerated(EnumType.STRING)
    private StatusPaiement statusPaiement;

    // Getters and Setters
}

