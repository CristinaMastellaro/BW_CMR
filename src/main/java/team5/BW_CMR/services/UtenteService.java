package team5.BW_CMR.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team5.BW_CMR.entities.Utente;
import team5.BW_CMR.exceptions.NotFoundException;
import team5.BW_CMR.repositories.UtenteRepository;

import java.util.UUID;

@Service
@Slf4j
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private PasswordEncoder bcrypt;

    public Utente findById(UUID id) { return this.utenteRepository.findById(id).orElseThrow(() -> new NotFoundException("Nessun user corrispondente a questo ID!"));}
}
