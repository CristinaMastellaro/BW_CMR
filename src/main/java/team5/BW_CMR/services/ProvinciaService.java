package team5.BW_CMR.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team5.BW_CMR.entities.Provincia;
import team5.BW_CMR.exceptions.NotFoundException;
import team5.BW_CMR.repositories.ProvinciaRepository;

@Service
@Slf4j
public class ProvinciaService {
    @Autowired
    private ProvinciaRepository pRepo;

    public Provincia saveProvincia(Provincia newProvincia) {
        return pRepo.save(newProvincia);
    }

    public Page<Provincia> findAllProvince(int page, int size, String sortBy) {
        if (size > 40) size = 40;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return pRepo.findAll(pageable);
    }

    public Provincia findProvinciaByNome(String nomeProvincia) {
        Provincia provincia = pRepo.findByProvincia(nomeProvincia);
        if (provincia == null)
            throw new NotFoundException("Non è stata trovata nessuna provincia chiamata " + nomeProvincia);
        return provincia;
    }

    public Provincia findProvinciaById(long id) {
        return pRepo.findById(id).orElseThrow(() -> new NotFoundException("Non è stata trovata nessuna provincia con id " + id));
    }
}
