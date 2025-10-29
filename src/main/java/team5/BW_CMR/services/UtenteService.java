package team5.BW_CMR.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team5.BW_CMR.entities.Ruolo;
import team5.BW_CMR.entities.Utente;
import team5.BW_CMR.exceptions.BadRequestException;
import team5.BW_CMR.payloads.UtenteDTO;
import team5.BW_CMR.repositories.RuoloRepository;
import team5.BW_CMR.repositories.UtenteRepository;

import java.io.IOException;
import java.util.*;

@Service
public class UtenteService {
    private static final long MAX_SIZE = 5 * 948 * 948;
    private static final Set<String> ALLOWED_TYPES = Set.of(
            "image/jpg",
            "image/png",
            "image/jpeg"
    );
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private RuoloService ruoloService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Cloudinary avtarUploader;
    @Autowired
    private RuoloRepository ruoloRepository;

    //crea nuovo utente
    public Utente salvaUtente(UtenteDTO dto) {
        Ruolo ruolo = ruoloService.getRuoloById(1);
        Utente utente = new Utente(dto.getUsername(), dto.getEmail(), passwordEncoder.encode(dto.getPassword()), dto.getFirstname(), dto.getLastname(), ruolo);
//        utente.setUsername(dto.getUsername());
//        utente.setEmail(dto.getEmail());
//        utente.setPassword(passwordEncoder.encode(dto.getPassword()));
//        utente.setFirstname(dto.getFirstname());
//        utente.setLastname(dto.getLastname());
        // utente.setAvatarUrl(dto.getAvatarUrl());


        return utenteRepository.save(utente);
    }


//        //assegna ruolo
//        Ruolo ruolo;
//        if(dto.isAdmin()) {
//            ruolo = ruoloRepository.findByNome("ADMIN")
//                    .orElseGet(() -> ruoloRepository.save(new Ruolo("ADMIN")));
//        } else {
//            ruolo = ruoloRepository.findByNome("USER")
//                    .orElseGet(() -> ruoloRepository.save(new Ruolo("USER")));
//        }
//
//        utente.getRuoli().add(ruolo);
//
//        return utenteRepository.save(utente);
//    }

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

    //upgrade utente a admin
    @Transactional
    public Utente promuoviAdmin(UUID id) {
        Utente utente = getUtenteById(id);
        Ruolo admin = ruoloRepository.findByNome("ADMIN").orElseGet(() -> ruoloRepository.save(new Ruolo("ADMIN")));
//        Ruolo admin = ruoloService.getRuoloById(2);
//        Set<Ruolo> ruoli = utente.getRuoli();
        Set<Ruolo> ruoli = utente.getRuoli();
        ruoli.add(admin);
        utente.setRuoli(ruoli);
        return utenteRepository.save(utente);
    }

    //patch avatar
    public Utente avatarUploader(MultipartFile file, UUID id) {
        Utente found = this.getUtenteById(id);
        if (file.isEmpty()) throw new BadRequestException("file vuoto");
        if (file.getSize() > MAX_SIZE) throw new BadRequestException("dimensione file troppo grande");
        if (!ALLOWED_TYPES.contains(file.getContentType())) throw new BadRequestException("formato non valido");
        try {
            Map result = avtarUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) result.get("url");
            found.setAvatarUrl(imageUrl);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        this.utenteRepository.save(found);
        return found;
    }

    @Transactional
    public boolean existsByRuolo(Ruolo ruolo) {
//        System.out.println("Utenti: " + getAllUtenti());
//        System.out.println("Utenti: " + getAllUtenti().stream().filter(u -> {
//            System.out.println(u.getRuoli());
//            return u.getRuoli().contains(ruolo);
//        }).toList());
//        return !getAllUtenti().stream().filter(u -> u.getRuoli().contains(ruolo)).toList().isEmpty();
        return !utenteRepository.findAllByRuolo(ruolo).isEmpty();
    }

}
