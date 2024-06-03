package ma.cabinetdentaire.repository;
import ma.cabinetdentaire.entity.Utilisateur;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Utilisateur, Long> {
    @Override
    List<Utilisateur> findAll();
    Utilisateur findByUsername(String username);
}

