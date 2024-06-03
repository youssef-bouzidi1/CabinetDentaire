package ma.cabinetdentaire.entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @ManyToOne
    private Role role;

    @OneToOne
    private Personne personne;

    // Getters and Setters
}

