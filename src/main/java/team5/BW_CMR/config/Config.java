package team5.BW_CMR.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import team5.BW_CMR.entities.Ruolo;
import team5.BW_CMR.entities.Utente;
import team5.BW_CMR.repositories.UtenteRepository;
import team5.BW_CMR.services.RuoloService;
import team5.BW_CMR.services.UtenteService;

@Configuration
public class Config {

    @Autowired
    private RuoloService ruoloService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UtenteRepository utenteRepository;

    @Bean
    public Utente utenteAdmin(@Value("${jwt.secret}") String password) {
        Ruolo admin = ruoloService.getRuoloById(2);
        Utente utente = new Utente("aldo","altraemail@email.com", passwordEncoder.encode(password), "aldo", "baglio", admin);
        utenteRepository.save(utente);

        return utente;
    }
}
