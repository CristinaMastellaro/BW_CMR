package team5.BW_CMR.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team5.BW_CMR.entities.Ruolo;
import team5.BW_CMR.entities.Utente;

import team5.BW_CMR.repositories.UtenteRepository;
import team5.BW_CMR.repositories.RuoloRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private RuoloRepository ruoloRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //crea nuovo utente
    public Utente salvaUtente(Utente utente) {
        if(utenteRepository.existsByEmail(utente.getEmail())) {
            throw new RuntimeException("email gia registrata");
        }
        if (utenteRepository.existsByUsername(utente.getUsername())) {
            throw new RuntimeException("username gia in uso");
        }

        utente.setPassword(passwordEncoder.encode(utente.getPassword()));

        //aggiunge ruolo user di default
        if (utente.getRuoli() == null || utente.getRuoli().isEmpty()) {
            Ruolo userRole = ruoloRepository.findByNome("USER")
                    .orElseGet(() -> ruoloRepository.save(new Ruolo("USER")));
            utente.getRuoli().add(userRole);
        }

        return utenteRepository.save(utente);
    }

    public List<Utente> getAllUtenti() {
        return utenteRepository.findAll();
    }

    public Utente getUtenteById(UUID id) {
        return utenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("utente non trovato"));
    }

    public void eliminaUtente(UUID id) {
        utenteRepository.deleteById(id);
    }

    public Optional<Utente> findByEmail(String email) {
        return utenteRepository.findByEmail(email);
    }
}
