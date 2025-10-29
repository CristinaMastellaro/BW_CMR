package team5.BW_CMR.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
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

    //post http://localhost:3011/api/fatture-stati
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FatturaStato create(@RequestBody @Valid FatturaStatoDTO dto) {
        return fsService.create(dto);
    }

    //put http://localhost:3001/api/fatture-stati/{id}

    @PutMapping("/{id}")
    public FatturaStato update(@PathVariable UUID id, @RequestBody @Valid FatturaStatoDTO dto) {
        return fsService.update(id, dto);
    }

    //delete http://localhost.3001/api/fatture-stati/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        fsService.delete(id);
    }
//get tutti gli stati di una fattura GET http://localhost:3001/api/fatture-stati/fattura/{fatturaId}?page=0&size=10
    @GetMapping("/fattura/{fatturaId}")
    public Page<FatturaStato> getByFattura(
            @PathVariable UUID fatturaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return fsService.getByFattura(fatturaId, page, size);
    }

    //get ultimo stato di una fattura GET http://localhost:3001/api/fatture-stati/fattura/{fatturaId}/
    @GetMapping("/fattura/{fatturaId}/ultimo")
    public FatturaStato getUltimoStato(@PathVariable UUID fatturaId) {
        return fsService.getUltimoStato(fatturaId);
    }


    //get stati per tipo GET http://localhost:3001/api/fatture-stati/tipo?statoFattura=
    @GetMapping("/tipo")
    public Page<FatturaStato> getByStatoFattura(
            @RequestParam StatoFattura statoFattura,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return fsService.getByStatoFattura(statoFattura, page, size);
    }

    // get x data precisa GET http://localhost:3001/api/fatture-stati/fattura/{fatturaId}/data?data=
    @GetMapping("/fattura/{fatturaId}/data")
    public Page<FatturaStato> getStatoByFatturaAndData(
            @PathVariable UUID fatturaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return fsService.getStatoByFatturaAndData(fatturaId, data, page, size);
    }

    @GetMapping("/filter")
    public Page<FatturaStato> filterStati(
            @RequestParam(required = false) UUID fatturaId,
            @RequestParam(required = false) StatoFattura statoFattura,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataMin,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataMax,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return fsService.filterStati(fatturaId, statoFattura, dataMin, dataMax, page, size);
    }
}