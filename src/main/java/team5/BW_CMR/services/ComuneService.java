package team5.BW_CMR.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team5.BW_CMR.entities.Comune;
import team5.BW_CMR.repositories.ComuneRepository;

import java.util.List;

@Service
@Slf4j
public class ComuneService {
    @Autowired
    private ComuneRepository cRepo;

    public Comune saveComune(Comune newComune) {
        return cRepo.save(newComune);
    }

    public List<Comune> findAllComuni() {
        return cRepo.findAll();
    }
}
