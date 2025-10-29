package team5.BW_CMR.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team5.BW_CMR.entities.Ruolo;
import team5.BW_CMR.exceptions.NotFoundException;
import team5.BW_CMR.payloads.RuoloDTO;
import team5.BW_CMR.repositories.RuoloRepository;

import java.util.List;
import java.util.UUID;

@Service
public class RuoloService {

    @Autowired
    private RuoloRepository ruoloRepository;

    public Ruolo saveRuolo(RuoloDTO dto) {
        Ruolo ruolo = new Ruolo(dto.nome());
        return ruoloRepository.save(ruolo);
    }

    public Ruolo getRuoloById(long id) {
        return ruoloRepository.findById(id).orElseThrow(()-> new NotFoundException("nessun ruolo trovato con id" + id));
    }

    public List<Ruolo> findAll() {
        return ruoloRepository.findAll();
    }
}
