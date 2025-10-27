package team5.BW_CMR.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team5.BW_CMR.entities.Fattura;
import team5.BW_CMR.entities.StatoFattura;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface FatturaRepository extends JpaRepository<Fattura, UUID> {

    //filtro x stato

    List<Fattura> findByStato(StatoFattura stato);

    //filtro x cliente
    List<Fattura> findByCliente_Id(UUID clienteId);

    //filtro x data
    List<Fattura> findByData(LocalDate data);

    //filtro x anno
    List<Fattura> findDataBetween(LocalDate start, LocalDate end);

    //filtro x range di importi

    List<Fattura> findImportoBetween (double min, double max);

}
