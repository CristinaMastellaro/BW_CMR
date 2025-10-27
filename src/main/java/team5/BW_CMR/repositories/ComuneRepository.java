package team5.BW_CMR.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team5.BW_CMR.entities.Comune;

@Repository
public interface ComuneRepository extends JpaRepository<Comune, Long> {
    Comune findByDenominazioneComune(String denominazioneComune);
}
