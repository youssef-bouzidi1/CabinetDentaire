package ma.cabinetdentaire.entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Acte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @Enumerated(EnumType.STRING)
    private CategorieActe categorieActe;
}
