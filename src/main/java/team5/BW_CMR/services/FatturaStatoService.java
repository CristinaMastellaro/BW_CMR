package team5.BW_CMR.services;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import team5.BW_CMR.entities.Fattura;
import team5.BW_CMR.entities.FatturaStato;
import team5.BW_CMR.entities.StatoFattura;
import team5.BW_CMR.exceptions.NotFoundException;
import team5.BW_CMR.payloads.FatturaStatoDTO;
import team5.BW_CMR.repositories.FatturaRepository;
import team5.BW_CMR.repositories.FatturaStatoRepository;
import team5.BW_CMR.specification.FatturaStatoSpecification;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class FatturaStatoService {

    private final FatturaStatoRepository fsRepo;
    private final FatturaRepository fRepo;

    public FatturaStatoService(FatturaStatoRepository fsRepo, FatturaRepository fRepo) {
        this.fsRepo = fsRepo;
        this.fRepo = fRepo;
    }

    public FatturaStato create(FatturaStatoDTO dto) {
        Fattura fattura = fRepo.findById(dto.fatturaId())
                .orElseThrow(() -> new NotFoundException("Fattura non trovata con id: " + dto.fatturaId()));

        FatturaStato nuovoStato = new FatturaStato(dto.statoFattura(), dto.dataStato(), fattura);
        return fsRepo.save(nuovoStato);
    }

    public FatturaStato update(UUID id, FatturaStatoDTO dto) {
        FatturaStato existing = fsRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Stato fattura non trovato con id " + id));
        existing.setStatoFattura(dto.statoFattura());
        existing.setDataStato(dto.dataStato());
        return fsRepo.save(existing);
    }

    public void delete(UUID id) {
        FatturaStato stato = fsRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Stato fattura non trovato con id " + id));
        fsRepo.delete(stato);
    }

    public Page<FatturaStato> getByFattura(UUID fatturaId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dataStato").descending());
        Page<FatturaStato> result = fsRepo.findByFatturaId(fatturaId, pageable);
        if (result.isEmpty()) {
            throw new NotFoundException("Nessuno stato trovato per la fattura " + fatturaId);
        }
        return result;
    }

    public FatturaStato getUltimoStato(UUID fatturaId) {
        Pageable pageable = PageRequest.of(0, 1, Sort.by("dataStato").descending());
        return fsRepo.findLastByFatturaId(fatturaId, pageable).stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Nessuno stato trovato per la fattura " + fatturaId));
    }

    public Page<FatturaStato> getByStatoFattura(StatoFattura stato, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dataStato").descending());
        Page<FatturaStato> result = fsRepo.findByStatoFattura(stato, pageable);
        if (result.isEmpty()) {
            throw new NotFoundException("Nessuno stato trovato con tipo: " + stato);
        }
        return result;
    }

    public Page<FatturaStato> getStatoByFatturaAndData(UUID fatturaId, LocalDate data, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FatturaStato> result = fsRepo.findByIdAndData(fatturaId, data, pageable);
        if (result.isEmpty()) {
            throw new NotFoundException("Nessuno stato trovato in data " + data);
        }
        return result;
    }

    public Page<FatturaStato> filterStati(UUID fatturaId, StatoFattura statoFattura, LocalDate dataMin, LocalDate dataMax, int page, int size) {
        Specification<FatturaStato> spec = null;

        if (fatturaId != null) {
            spec = spec.and(new FatturaStatoSpecification("fatturaId", ":", fatturaId));
        }

        if (statoFattura != null) {
            spec = spec.and(new FatturaStatoSpecification("statoFattura", ":", statoFattura));
        }

        if (dataMin != null) {
            spec = spec.and(new FatturaStatoSpecification("dataStato", ">", dataMin));
        }

        if (dataMax != null) {
            spec = spec.and(new FatturaStatoSpecification("dataStato", "<", dataMax));
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("dataStato").descending());
        Page<FatturaStato> result = fsRepo.findAll(spec, pageable);

        if (result.isEmpty()) {
            throw new NotFoundException("Nessuno stato trovato con i criteri specificati.");
        }

        return result;
    }
}