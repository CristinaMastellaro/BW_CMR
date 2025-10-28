package team5.BW_CMR.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team5.BW_CMR.entities.Ruolo;
import team5.BW_CMR.entities.Utente;
import team5.BW_CMR.repositories.RuoloRepository;
import team5.BW_CMR.security.JwtTools;
import team5.BW_CMR.services.UtenteService;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTools jwtTools;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private RuoloRepository ruoloRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    //login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
                );

        Utente utente =(Utente) auth.getPrincipal();
        String token = jwtTools.createToken(utente);

        return ResponseEntity.ok(Map.of(
                "token", token,
                "username", utente.getUsername(),
                "ruoli", utente.getRuoli()
        ));
    }

    //registrazione
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Utente nuovoUtente) {
        Utente utente = utenteService.salvaUtente(nuovoUtente);

        //ruolo di default user
        Ruolo userRole = ruoloRepository.findByNome("USER")
                .orElseGet(() -> ruoloRepository.save(new Ruolo("USER")));
        Set<Ruolo> ruoli = new HashSet<>();
        ruoli.add(userRole);
        utente.setRuoli(ruoli);
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        utenteService.salvaUtente(utente);

        return ResponseEntity.ok(Map.of("message", "utente registrato"));
    }
}
