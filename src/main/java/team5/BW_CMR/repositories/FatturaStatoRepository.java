package team5.BW_CMR.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import team5.BW_CMR.entities.FatturaStato;
import team5.BW_CMR.entities.StatoFattura;

import java.time.LocalDate;
import java.util.UUID;

public interface FatturaStatoRepository extends JpaRepository<FatturaStato, UUID>, JpaSpecificationExecutor <FatturaStato> {

    //cerco tutti gli stati di una fattura
    @Query("SELECT fs FROM FatturaStato fs WHERE fs.fattura.id= :fatturaId")
    Page<FatturaStato> findByFatturaId(UUID fatturaId, Pageable pageable);

    //cerco stati x tipo

    @Query("SELECT fs FROM FatturaStato fs WHERE fs.statoFattura= :stato")
    Page<FatturaStato> findByStatoFattura(StatoFattura stato, Pageable pageable);

    //cerco stati successiva a una determinata data
    @Query("SELECT fs FROM FatturaStato fs WHERE fs.dataStato > :data")
    Page<FatturaStato> findByDataStatoAfter(LocalDate data, Pageable pageable);

    // cerco stato x data precisa
@Query("SELECT fs FROM FatturaStato fs WHERE fs.fattura.id= :fatturaId AND fs.dataStato = :data")
    Page<FatturaStato> findByIdAndData(UUID fatturaId, LocalDate data, Pageable pageable);

//cerco stati prima di una determinata data

    @Query("SELECT fs FROM FatturaStato fs WHERE fs.dataStato < :data")
    Page<FatturaStato> findByDataStatoBefore(LocalDate data, Pageable pageable);

    //cerco ultimo stato di una fattura (uso order by desc x avere l'ultimo stato preciso)

    @Query("SELECT fs FROM FatturaStato fs WHERE fs.fattura.id = :fatturaId ORDER BY fs.dataStato DESC")
    Page<FatturaStato> findLastByFatturaId(UUID fatturaID, Pageable pageable);
}
