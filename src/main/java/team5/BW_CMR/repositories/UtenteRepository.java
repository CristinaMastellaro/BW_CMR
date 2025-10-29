package team5.BW_CMR.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team5.BW_CMR.entities.Ruolo;
import team5.BW_CMR.entities.Utente;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, UUID> {

    Optional<Utente> findByEmail(String email);

    Optional<Utente> findByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    @Query("SELECT u FROM Utente u WHERE :ruolo MEMBER OF u.ruoli")
    List<Utente> findAllByRuolo(@Param("ruolo") Ruolo ruolo);
}
