package team5.BW_CMR.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team5.BW_CMR.entities.Comune;
import team5.BW_CMR.exceptions.NotFoundException;
import team5.BW_CMR.repositories.ComuneRepository;

@Service
@Slf4j
public class ComuneService {
    @Autowired
    private ComuneRepository cRepo;

    public Comune saveComune(Comune newComune) {
        return cRepo.save(newComune);
    }

    public Page<Comune> findAllComuni(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return cRepo.findAll(pageable);
    }

    public Comune findComuneByDenominazione(String denominazione) {
        Comune comune = cRepo.findByDenominazioneComune(denominazione);
        if (comune == null)
            throw new NotFoundException("Non è stato trovato nessuno comune denominato " + denominazione);
        return comune;
    }

    public Comune findComuneById(long id) {
        return cRepo.findById(id).orElseThrow(() -> new NotFoundException("Non c'è nessun comune con id " + id));
    }
}
