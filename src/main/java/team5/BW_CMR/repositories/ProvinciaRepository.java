package team5.BW_CMR.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team5.BW_CMR.entities.Provincia;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {
    Provincia findByProvincia(String provincia);
}
