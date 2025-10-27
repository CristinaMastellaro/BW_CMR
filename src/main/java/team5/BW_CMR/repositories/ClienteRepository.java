package team5.BW_CMR.repositories;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team5.BW_CMR.entities.Cliente;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    Optional<Cliente> findById(UUID id);

    boolean existsByEmail(String email);
    boolean existsByPec(String pec);
    boolean existsByEmailContatto(String emailContatto);
    boolean existsByTelefonoContatto( long telefonoContatto);
    boolean existsByPartitaIva(long partitaIva);

}
