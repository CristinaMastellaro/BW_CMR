package team5.BW_CMR.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team5.BW_CMR.entities.Indirizzo;
import team5.BW_CMR.payloads.IndirizzoDTO;
import team5.BW_CMR.repositories.IndirizzoRepository;

@Service
@Slf4j
public class IndirizzoService {
    @Autowired
    private IndirizzoRepository iRepo;

    public Indirizzo saveIndirizzo(IndirizzoDTO newIndirizzo) {

    }
}
