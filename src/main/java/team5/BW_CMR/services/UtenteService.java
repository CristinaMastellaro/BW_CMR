package team5.BW_CMR.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team5.BW_CMR.entities.Utente;

import team5.BW_CMR.repositories.UtenteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;

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

        return utenteRepository.save(utente);
    }

    public List<Utente> getAllUtenti() {
        return utenteRepository.findAll();
    }

    public Utente getUtenteById(Long id) {
        return utenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("utente non trovato"));
    }

    public void eliminaUtente(Long id) {
        utenteRepository.deleteById(id);
    }

    public Optional<Utente> findByEmail(String email) {
        return utenteRepository.findByEmail(email);
    }
}
