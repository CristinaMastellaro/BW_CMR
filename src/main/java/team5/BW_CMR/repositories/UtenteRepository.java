package team5.BW_CMR.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team5.BW_CMR.entities.Utente;

import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {

    Optional<Utente> findByEmail(String email);
    Optional<Utente> findByUsername(String username);

    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
}
