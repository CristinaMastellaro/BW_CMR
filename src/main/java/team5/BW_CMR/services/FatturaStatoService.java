package team5.BW_CMR.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team5.BW_CMR.entities.Fattura;
import team5.BW_CMR.entities.FatturaStato;
import team5.BW_CMR.entities.StatoFattura;
import team5.BW_CMR.exceptions.BadRequestException;
import team5.BW_CMR.exceptions.NotFoundException;
import team5.BW_CMR.payloads.FatturaDTO;
import team5.BW_CMR.payloads.FatturaStatoDTO;
import team5.BW_CMR.repositories.FatturaRepository;
import team5.BW_CMR.repositories.FatturaStatoRepository;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class FatturaStatoService {
    private final FatturaStatoRepository fsRepo;
    private final FatturaRepository fRepo;

    public FatturaStatoService(FatturaStatoRepository fsRepo, FatturaRepository fRepo) {
        this.fsRepo = fsRepo;
        this.fRepo = fRepo;
    }


    //creo nuovo stato fattura
    public FatturaStato create(FatturaStatoDTO dto) {
        Fattura fattura = fRepo.findById(dto.fatturaId())
                .orElseThrow(() -> new NotFoundException("Fattura non trovata con id: " + dto.fatturaId()));

        FatturaStato nuovoStato = new FatturaStato(
                dto.statoFattura(),
                dto.dataStato(),
                fattura
        );

        return fsRepo.save(nuovoStato);
    }

// tutti gli stati di una fattura

    public Page<FatturaStato> getByFattura(UUID fatturaId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dataStato").descending());
        Page<FatturaStato> result = fsRepo.findByFatturaId(fatturaId, pageable);
        if (result.isEmpty()) {
            throw new NotFoundException("Nessuno stato trovato per questa fattura!" + fatturaId);
        }
        return result;
    }

    //ultimo stato di una fattura

    public FatturaStato getUltimoStato(UUID fatturaId) {
        Pageable pageable = PageRequest.of(0, 1, Sort.by("dataStato").descending());
        return fsRepo.findLastByFatturaId(fatturaId, pageable).stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Nessuno stato trovato per la fattura " + fatturaId));
    }

//per stato fattura

    public Page<FatturaStato> getByStatoFattura(StatoFattura stato, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dataStato").descending());
        Page<FatturaStato> result = fsRepo.findByStatoFattura(stato, pageable);
        if (result.isEmpty()) {
            throw new NotFoundException("Nessuno stato trovato con tipo:" + stato);
        }
        return result;
    }

/*
    // stati dopo una determinata data

    public Page<FatturaStato> getStatiDopo(LocalDate data, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dataStato"));
        Page<FatturaStato> result = fsRepo.findByDataStatoAfter(data, pageable);
        if (result.isEmpty()) {
            throw new NotFoundException("Nessuno stato trovato per la data" + data);
        }
        return result;
    }

    // stati prima di una determinata data

    public Page<FatturaStato> getStatoPrima(LocalDate data, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dataStato"));
        Page<FatturaStato> result = fsRepo.findByDataStatoBefore(data, pageable);
        if (result.isEmpty()) {
            throw new NotFoundException("Nessuno stato trovato per la data" + data);
        }
        return result;
    }
*/



    //stato in una determinata data

    public Page<FatturaStato> getStatoByFatturaAndData(UUID fatturaId, LocalDate data, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FatturaStato> result = fsRepo.findByIdAndData(fatturaId, data, pageable);
        if (result.isEmpty()) {
            throw new NotFoundException("Nessuno stato trovato in questa data" + data);
        }
        return result;
    }
    //delete

    public void delete(UUID id) {
        FatturaStato stato = fsRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Stato fattura non trovato con id " + id));
        fsRepo.delete(stato);
    }

//update

    public FatturaStato update(UUID id, FatturaStatoDTO dto) {
        FatturaStato existing = fsRepo.findById(id).orElseThrow(() -> new NotFoundException(" Fattura non trovata con id " + dto.fatturaId()));
        existing.setStatoFattura(dto.statoFattura());
        existing.setDataStato(dto.dataStato());


        return fsRepo.save(existing);
    }
}




