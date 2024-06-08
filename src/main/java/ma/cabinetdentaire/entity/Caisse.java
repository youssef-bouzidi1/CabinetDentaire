package ma.cabinetdentaire.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Caisse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double solde;

    @OneToMany(mappedBy = "caisse")
    private List<SituationFinanciere> situationsFinancieres;

    // Getters and Setters
}
