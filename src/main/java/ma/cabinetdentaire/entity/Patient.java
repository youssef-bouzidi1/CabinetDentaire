package ma.cabinetdentaire.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
@Entity
public class Patient {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Enumerated(EnumType.STRING)
        private GroupSanguin groupSanguin;

        @OneToOne
        private Personne personne;

        @Enumerated(EnumType.STRING)
        private Mutuelle mutuelle;

        @OneToOne(fetch = FetchType.EAGER,mappedBy = "patient")
        private DossierMedicale dossierMedicals;

        private String antecedentMedicals;
        public List<String> getAntecedentMedicalsList() {
                return Arrays.asList(this.antecedentMedicals.split(","));
        }}
