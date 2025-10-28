package team5.BW_CMR.repositories;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team5.BW_CMR.entities.Cliente;

import java.time.LocalDate;
import java.util.List;
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


    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nomeContatto) LIKE LOWER(CONCAT('%', :parteNome, '%'))")
    Page<Cliente> findByParteNomeContatto(@Param("parteNome") String parteNome, Pageable pageable);

    Page<Cliente> findByFatturatoAnnuale(double fatturatoAnnuale, Pageable pageable);
    Page<Cliente> findByDataInserimento(LocalDate dataInserimento, Pageable pageable);
    Page<Cliente> findByDataUltimoContatto(LocalDate dataUltimoContatto, Pageable pageable);

}
