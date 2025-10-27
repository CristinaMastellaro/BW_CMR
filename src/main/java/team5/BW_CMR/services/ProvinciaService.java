package team5.BW_CMR.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team5.BW_CMR.entities.Provincia;
import team5.BW_CMR.repositories.ProvinciaRepository;

import java.util.List;

@Service
@Slf4j
public class ProvinciaService {
    @Autowired
    private ProvinciaRepository pRepo;

    public Provincia saveProvincia(Provincia newProvincia) {
        return pRepo.save(newProvincia);
    }

    public List<Provincia> findAllProvince() {
        return pRepo.findAll();
    }
}
