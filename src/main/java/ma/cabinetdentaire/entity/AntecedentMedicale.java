package ma.cabinetdentaire.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class AntecedentMedicale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private LocalDate dateAntecedent;

    @ManyToOne
    private Patient patient;

    @Enumerated
    private CategorieAntecedentMedical categorieAntecedentMedical;

    // Getters and Setters
}

