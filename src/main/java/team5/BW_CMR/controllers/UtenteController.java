package team5.BW_CMR.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team5.BW_CMR.entities.Utente;
import team5.BW_CMR.payloads.UtenteDTO;
import team5.BW_CMR.services.UtenteService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

//    @PostMapping
//    public ResponseEntity<?> creaUtente(@RequestBody UtenteDTO dto, Authentication auth) {
//        if (dto.isAdmin()) {
//            if (auth == null) {
//                return ResponseEntity.status(403).body("autenticazione fallita");
//            }
//            Utente requester = (Utente) auth.getPrincipal();
//            boolean isAdmin = requester.getAuthorities().stream()
//                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
//            if (!isAdmin) {
//                return ResponseEntity.status(403).body("solo un admin può creare un altro admin");
//            }
//        }
//
//        Utente nuovo = utenteService.salvaUtente(dto);
//        return new ResponseEntity<>(nuovo, HttpStatus.CREATED);
//    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Utente>> getAll() {
        return ResponseEntity.ok(utenteService.getAllUtenti());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Utente> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(utenteService.getUtenteById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> elimina(@PathVariable UUID id) {
        utenteService.eliminaUtente(id);
        return  ResponseEntity.noContent().build();
    }

    // passa ad admin
    @PutMapping("/{id}/promuovi")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Utente> promuoviAdmin(@PathVariable UUID id) {
        return ResponseEntity.ok(utenteService.promuoviAdmin(id));
    }

    //patch avatar
    @PatchMapping("/{id}/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public Utente uploadAvatar(@PathVariable UUID id, @RequestParam("avatarUrl")MultipartFile file) throws IOException {
        return this.utenteService.avatarUploader(file, id);
    }
}
