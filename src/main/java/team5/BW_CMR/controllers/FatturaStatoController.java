package team5.BW_CMR.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team5.BW_CMR.entities.FatturaStato;
import team5.BW_CMR.entities.StatoFattura;
import team5.BW_CMR.payloads.FatturaStatoDTO;
import team5.BW_CMR.services.FatturaStatoService;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/fatture-stati")
public class FatturaStatoController {
    @Autowired
    private FatturaStatoService fsService;

    /* metodo post http://localhost:3001/api/fatture-stati
    metodo get (tutti gli stati di una fattura) http://localhost:3001/api/fatture-stati/fattura/{fatturaId}?page=0&size=10
    metodo get (l'ultimo stato di una fattura) http://localhost:3001/api/fatture-stati/fattura/{fatturaId}/ultimo
    metodo get (otteniamo stati per tipo) http://localhost:3001/api/fatture-stati/tipo?stato=INVIATA&page=0&size=10
    metodo get (otteniamo stato fattura dopo una determinata data) http://localhost:3001/api/fatture-stati/dopo?data=2025-01-01&page=0&size=10
    metodo get (otteniamo stato fattura prima di una determinata data) http://localhost:3001/api/fatture-stati/prima?data=2025-01-01&page=0&size=10
    metodo get (otteniamo stato fattura in una data precisa) http://localhost:3001/api/fatture-stati/fattura/{fatturaId}/data?data=2025-10-28&page=0&size=10
     metodo put http://localhost:3001/api/fatture-stati/{id}
     metodo delete http://localhost:3001/api/fatture-stati/{id}
     */

    //metodo post
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FatturaStato create(@RequestBody @Valid FatturaStatoDTO dto){
        return fsService.create(dto);
    }

    // metodo get (tutti gli stati di una fattura)

    @GetMapping ("/fattura/{fatturaId}")
    public Page<FatturaStato> getByFattura(
            @PathVariable UUID fatturaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue ="10") int size
    ){
        return fsService.getByFattura(fatturaId, page,size);
    }

    //metodo get ultimo stato di una fattura
@GetMapping("/fattura/{fatturaId}(ultimo")
    public FatturaStato getUltimoStato(@PathVariable UUID fatturaId){
        return fsService.getUltimoStato(fatturaId);
}

//metodo get stati x tipo

    @GetMapping("/tipo")
    public Page <FatturaStato> getByStatoFattura(
            @RequestParam StatoFattura stato,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ){
        return fsService.getByStatoFattura(stato, page, size);
    }

    // metodo get stati dopo una determinata data

    @GetMapping("/dopo")
    public Page <FatturaStato> getStatiDopo(
            @RequestParam LocalDate data,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ){
        return fsService.getStatiDopo(data, page, size);
    }

    //metodo get per stati prima di una determinata data

    @GetMapping("/prima")
    public Page <FatturaStato> getStatoPrima(
            @RequestParam LocalDate data,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size
            ){
        return fsService.getStatoPrima(data, page, size);
    }

    // metodo get x stati in una data precisa
    @GetMapping("fattura/{fatturaId}/data")
    public Page<FatturaStato> getStatoByFatturaAndData(
            @PathVariable UUID fatturaId,
            @RequestParam LocalDate data,
            @RequestParam (defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return fsService.getStatoByFatturaAndData(fatturaId,data,page,size);
    }

    //metodo put
    @PutMapping("/{id}")
    public FatturaStato update(@PathVariable UUID id, @RequestBody @Valid FatturaStatoDTO dto){
        return fsService.update(id, dto);
    }
    // metodo delete

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
        fsService.delete(id);
    }
}
