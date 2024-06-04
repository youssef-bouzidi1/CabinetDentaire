package ma.cabinetdentaire.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Data
@Entity
public class Dentist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Utilisateur utilisateur;

    @ElementCollection
    @MapKeyEnumerated(EnumType.STRING)
    private Map<DayOfWeek, Disponibilite> disponibilite;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Specialite> specialite;
    private Assurance assurance;
    @Enumerated(EnumType.STRING)
    private StatutEmploye statutActuel;
}

