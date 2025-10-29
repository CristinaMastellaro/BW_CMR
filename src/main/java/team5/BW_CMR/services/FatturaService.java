package team5.BW_CMR.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import team5.BW_CMR.entities.Fattura;
import team5.BW_CMR.exceptions.NotFoundException;
import team5.BW_CMR.repositories.FatturaRepository;
import team5.BW_CMR.specification.FatturaSpecification;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FatturaService {
    @Autowired
    private FatturaRepository fRepo;

    public List<Fattura> filterFatture(
            Double importoMin,
            Double importoMax,
            LocalDate dataMin,
            LocalDate dataMax,
            Long numero,
            UUID clienteId


    ) { //range di importi
        Specification<Fattura> spec = null;
        if (importoMin != null) {
            spec = spec.and(new FatturaSpecification("importo", ">", importoMin));
        }
        if (importoMax != null) {
            spec = spec.and(new FatturaSpecification("importo", ">", importoMax));

        }
//range data
        if (dataMin != null) {
            spec = spec.and(new FatturaSpecification("data", "<", dataMin));

        }

        if (dataMax != null) {
            spec = spec.and(new FatturaSpecification("data", ">", dataMax));

        }

        if (numero != null) {
            spec = spec.and(new FatturaSpecification("numero", ":", numero));

        }

        if (numero != null) {
            spec = spec.and((new FatturaSpecification("clienteId", ":", clienteId)));
        }
        return fRepo.findAll(spec);
    }



//spec rappresenta la condizione e and crea una nuova specification che combina le due condizioni(importo, data, cliente e num fattura)



    //salvo nuova fattura
    public Fattura saveFattura(Fattura newFattura){
        return fRepo.save(newFattura);
    }

    //cerco tutta le fattura

    public List<Fattura> findAll(){
        return fRepo.findAll();
    }
// cerco x clienteId
public List<Fattura> findByCliente_Id(UUID id){
        return fRepo.findByCliente_Id(id);
    }
//cerco x data
   public List<Fattura> findByData(LocalDate data){
        return fRepo.findByData(data);
    }
public void deleteFattura(UUID id){
        if(!fRepo.existsById(id)) {
            throw new NotFoundException("Fattura con id " + id + "Non trovata");
        }
        fRepo.deleteById(id);
        }
}
