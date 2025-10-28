package team5.BW_CMR.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import team5.BW_CMR.entities.Ruolo;

import java.util.Optional;

public interface RuoloRepository extends JpaRepository<Ruolo, Long> {
    Optional<Ruolo> findByNome(String nome);
}
