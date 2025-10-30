package team5.BW_CMR.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team5.BW_CMR.entities.Comune;
import team5.BW_CMR.entities.Indirizzo;
import team5.BW_CMR.exceptions.NotFoundException;
import team5.BW_CMR.payloads.IndirizzoDTO;
import team5.BW_CMR.repositories.IndirizzoRepository;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class IndirizzoService {
    @Autowired
    private IndirizzoRepository iRepo;
    @Autowired
    private ComuneService cServ;

    public Indirizzo saveIndirizzo(IndirizzoDTO newIndirizzo) {
        // Non ci sono particolari controlli da fare, se non controllare che il comune esiste
        Comune comune = cServ.findComuneByDenominazione(newIndirizzo.comune());

        Indirizzo indirizzo = new Indirizzo(newIndirizzo.via(), newIndirizzo.civico(), newIndirizzo.localita(), newIndirizzo.cap(), comune);

        iRepo.save(indirizzo);

        log.info("L'indirizzo " + indirizzo.getVia() + " è stato salvato correttamente!");

        return indirizzo;
    }

    public Indirizzo findIndirizzoById(UUID id) {
        return iRepo.findById(id).orElseThrow(() -> new NotFoundException("L'indirizzo con id " + id + " non è stato trovato"));
    }

    public List<Indirizzo> findAllAddresses() {
        return iRepo.findAll();
    }

    public Indirizzo updateIndirizzoById(UUID indirizzoId, IndirizzoDTO newInfo) {
        Indirizzo indirizzoDaModificare = findIndirizzoById(indirizzoId);
        if (indirizzoDaModificare.getComune().getDenominazioneComune().equals(newInfo.comune())) {
            Comune comune = cServ.findComuneByDenominazione(newInfo.comune());
            indirizzoDaModificare.setComune(comune);
        }

        indirizzoDaModificare.setVia(newInfo.via());
        indirizzoDaModificare.setCivico(newInfo.civico());
        indirizzoDaModificare.setCap(newInfo.cap());
        indirizzoDaModificare.setLocalita(newInfo.localita());

        iRepo.save(indirizzoDaModificare);

        log.info("Indirizzo aggiornato correttamente");

        return indirizzoDaModificare;
    }

    public void deleteIndirizzo(UUID id) {
        Indirizzo indirizzo = findIndirizzoById(id);

        iRepo.delete(indirizzo);

        log.info("L'indirizzo è stato cancellato!");
    }
}
