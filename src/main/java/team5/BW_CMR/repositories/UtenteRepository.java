package team5.BW_CMR.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team5.BW_CMR.entities.Provincia;
import team5.BW_CMR.entities.Utente;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, UUID> {
    Optional<Utente> findById(UUID id);
}
