package team5.BW_CMR.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team5.BW_CMR.entities.Comune;
import team5.BW_CMR.entities.Indirizzo;
import team5.BW_CMR.payloads.IndirizzoDTO;
import team5.BW_CMR.repositories.IndirizzoRepository;

@Service
@Slf4j
public class IndirizzoService {
    @Autowired
    private IndirizzoRepository iRepo;
    @Autowired
    private ComuneService cServ;

    public Indirizzo saveIndirizzo(IndirizzoDTO newIndirizzo) {
        // Non ci sono particolari controlli da fare
        Comune comune = cServ.findComuneByDenominazione(newIndirizzo.comune());

        Indirizzo indirizzo = new Indirizzo(newIndirizzo.via(), newIndirizzo.civico(), newIndirizzo.localita(), newIndirizzo.cap(), comune);

        iRepo.save(indirizzo);

        log.info("L'indirizzo " + indirizzo.getVia() + " è stato salvato correttamente!");

        return indirizzo;
    }
}
