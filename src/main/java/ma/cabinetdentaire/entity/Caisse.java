package ma.cabinetdentaire.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Caisse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double solde;

    @OneToMany(mappedBy = "caisse")
    private List<SituationFinanciere> situationsFinancieres;

    // Getters and Setters
}
