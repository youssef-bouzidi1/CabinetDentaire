package ma.cabinetdentaire.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double montantRestant;
    @OneToOne
    private SituationFinanciere situationFinanciere;
    private Double montantPaye;
    private LocalDate dateFacturation;
    private Double montantTotale;
    @ManyToOne
    private Consultation consultation;
    private TypePaiement typePaiement;
}
