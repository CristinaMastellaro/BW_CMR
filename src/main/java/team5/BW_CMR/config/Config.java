package team5.BW_CMR.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Config {

    //    @Autowired
//    private RuoloService ruoloService;
    @Autowired
    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private UtenteRepository utenteRepository;

//    @Bean
//    public Utente utenteAdmin(@Value("${jwt.secret}") String password) {
//        Ruolo admin = ruoloService.getRuoloById(2);
//        Utente utente = new Utente("aldo","altraemail@email.com", passwordEncoder.encode(password), "aldo", "baglio", admin);
//        utenteRepository.save(utente);
//
//        return utente;
//    }

    @Bean
    public String getEncodedPassword(@Value("${jwt.secret}") String password) {
        return passwordEncoder.encode(password);
    }
}
