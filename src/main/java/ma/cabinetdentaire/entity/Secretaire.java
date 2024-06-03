package ma.cabinetdentaire.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Secretaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Utilisateur utilisateur;
    private double salaireDeBase;
    private LocalDate dateRetourConge;
    private Assurance assurance;
    private StatutEmploye statutActuel;
}
