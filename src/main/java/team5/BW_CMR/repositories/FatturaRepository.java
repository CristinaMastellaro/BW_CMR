package team5.BW_CMR.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team5.BW_CMR.entities.Fattura;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface FatturaRepository extends JpaRepository<Fattura, UUID> {




    //filtro x cliente
    List<Fattura> findByCliente_Id(UUID clienteId);

    //filtro x data
    List<Fattura> findByData(LocalDate data);

    //filtro x anno
  @Query("SELECT f FROM Fattura f WHERE f.data BETWEEN : start AND :end")
    List<Fattura> findByDataBetween(@Param("start")LocalDate start,
                                    @Param("end")LocalDate end);
    //filtro x range di importi
@Query("SELECT f FROM Fattura f WHERE f.importo BETWEEN :min AND :max")
    List<Fattura> findByImportoBetween(@Param("min")double min,
                                       @Param("max")double max);

}
