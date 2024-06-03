package ma.cabinetdentaire.services;

import ma.cabinetdentaire.entity.Utilisateur;
import ma.cabinetdentaire.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testSaveUser() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setUsername("Ahmed");
        utilisateur.setPassword("ahmed@example.com");

        try {
            userRepository.save(utilisateur);
            // Don't forget to commit changes if everything is successful
            // con.commit();
        } catch (DataAccessException e) {
            e.printStackTrace(); // Log the exception
            // Handle the exception (rollback, display an error message, etc.)
        }
    }
}
