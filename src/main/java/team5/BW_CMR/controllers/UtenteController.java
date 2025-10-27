package team5.BW_CMR.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team5.BW_CMR.entities.Utente;
import team5.BW_CMR.services.UtenteService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @PostMapping
    public ResponseEntity<Utente> creaUtente(@RequestBody Utente utente) {
        Utente nuovo = utenteService.salvaUtente(utente);
        return new ResponseEntity<>(nuovo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Utente>> getAll() {
        return ResponseEntity.ok(utenteService.getAllUtenti());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utente> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(utenteService.getUtenteById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> elimina(@PathVariable UUID id) {
        utenteService.eliminaUtente(id);
        return  ResponseEntity.noContent().build();
    }
}
