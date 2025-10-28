package team5.BW_CMR.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team5.BW_CMR.entities.Fattura;
import team5.BW_CMR.exceptions.NotFoundException;
import team5.BW_CMR.repositories.FatturaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FatturaService {
    @Autowired
    private FatturaRepository fRepo;

    //salva nuova fattura
    public Fattura saveFattura(Fattura newFattura) {
        return fRepo.save(newFattura);
    }

    // cerca tutte le fattura
    public List<Fattura> findAll() {
        return fRepo.findAll();
    }

    //cerca per stato


    //cerca x cliente

    public List<Fattura> findByCliente(UUID clienteId) {
        return fRepo.findByCliente_Id(clienteId);
    }

    //cerca x data

    public List<Fattura> findByData(LocalDate data) {
        return fRepo.findByData(data);
    }

    //cerca x anno (dal primo gennaio al 31 dicembre)
    public List<Fattura> findByDataBetween(LocalDate start, LocalDate end) {
        return fRepo.findByDataBetween(start, end);
    }


    //cerca x range di importo

    public List<Fattura> findByImportoBetween(double min, double max) {
        return fRepo.findByImportoBetween(min, max);
    }


    public void deleteFattura(UUID id) {
        if (!fRepo.existsById(id)) {
            throw new NotFoundException("Fattura con id " + id + " non trovata");
        }
        fRepo.deleteById(id);

    }
}

