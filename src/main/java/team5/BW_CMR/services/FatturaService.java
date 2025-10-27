package team5.BW_CMR.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team5.BW_CMR.entities.Fattura;
import team5.BW_CMR.repositories.FatturaRepository;

import java.util.List;

@Service
@Slf4j
public class FatturaService {
    @Autowired
    private FatturaRepository fRepo;

    //salva nuova fattura
    public Fattura saveFattura( Fattura newFattura){
        return fRepo.save(newFattura);
    }

    // cerca tutte le fattura
    public List<Fattura> findAll(){
        return fRepo.findAll();
    }

    //cerca per stato

    
}
