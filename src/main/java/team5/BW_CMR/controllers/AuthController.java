package team5.BW_CMR.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import team5.BW_CMR.payloads.UtenteDTO;
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

        Utente utente = (Utente) auth.getPrincipal();
        String token = jwtTools.createToken(utente);

        return ResponseEntity.ok(Map.of(
                "token", token,
                "username", utente.getUsername(),
                "ruoli", utente.getRuoli()
        ));
    }

    //registrazione
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UtenteDTO nuovoUtente) {


        // Controllo per creare admin
       // if (Boolean.TRUE.equals(nuovoUtente.isAdmin())) {
//            if (auth == null) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                        .body("Autenticazione necessaria per creare un admin");
//            }

//            Utente requester = (Utente) auth.getPrincipal();
//            boolean isAdmin = requester.getAuthorities().stream()
//                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
//
//            if (!isAdmin) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                        .body("Solo un admin può creare un altro admin");
//            }
//        }

            // Salva utente (user di default o admin se richiesto)
            Utente utente = utenteService.salvaUtente(nuovoUtente);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "message", "Utente registrato",
                            "username", utente.getUsername(),
                            "ruoli", utente.getRuoli()
                    ));
        }
    }


