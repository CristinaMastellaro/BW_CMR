package team5.BW_CMR.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team5.BW_CMR.entities.Cliente;
import team5.BW_CMR.entities.Fattura;
import team5.BW_CMR.exceptions.BadRequestException;
import team5.BW_CMR.exceptions.NotFoundException;
import team5.BW_CMR.payloads.FatturaDTO;
import team5.BW_CMR.repositories.ClienteRepository;
import team5.BW_CMR.services.FatturaService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fatture")
public class FatturaController {
    @Autowired
    private FatturaService fatturaService;

    @Autowired
    private ClienteRepository clienteRepository;

    //metodo post  http://localhost:8888/api/fatture


    @PostMapping
    public Fattura create(@RequestBody @Valid FatturaDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.clienteID()).orElseThrow(() -> new NotFoundException("cliente non trovato con id:" + dto.clienteID()));

        if (dto.importo() <= 0) {
            throw new BadRequestException("L'importo deve essere maggiore di zero");

        }
        Fattura fattura = new Fattura(
                dto.data(),
                dto.importo(),
                dto.numero(),
                cliente
        );
        return fatturaService.saveFattura(fattura);
    }


    //Get tutte le fatture http://localhost:8888/api/fatture
    @GetMapping
    public List<Fattura> getAll() {
        return fatturaService.findAll();
    }



//get per cliente http://localhost:8888/api/fatture/cliente/{clienteId}

    @GetMapping("/cliente/{clienteId}")
    public List<Fattura> getByCliente(@PathVariable UUID clienteId) {
        List<Fattura> result = fatturaService.findByCliente(clienteId);
        if (result.isEmpty()) throw new NotFoundException("nassuna fattura trovata per il cliente con id " + clienteId);
        return result;
    }

    // get per data esatta http://localhost:8888/api/fattura/data/2025-10-27
    @GetMapping("/data/{data}")
    public List<Fattura> getByData(@PathVariable String data) {
        LocalDate parsedDate;// x convertire la stringa in local date
        try {
            parsedDate = LocalDate.parse(data);
        } catch (Exception e) {
            throw new BadRequestException("Formato data non valido.");
        }
        List<Fattura> result = fatturaService.findByData(parsedDate);
        if (result.isEmpty()) throw new NotFoundException("Nessuna fattura trovare per la data " + data);
        return result;
    }

    //get per anno http://localhost:8888/api/fatture/anno/2025

    @GetMapping("/date-range")
    public List<Fattura> getFatturaByDataRange(
            @RequestParam("start") LocalDate start,
            @RequestParam("end") LocalDate end) {

        List<Fattura> fatture = fatturaService.findByDataBetween(start, end);

        if (fatture.isEmpty()) {
            throw new NotFoundException("Nessuna fattura trovata tra " + start + " e " + end);
        }

        return fatture;
    }

    //get per importo
    @GetMapping("/importo-range")
    public List<Fattura> getFatturaByImportoRange(
            @RequestParam("min") double min,
            @RequestParam("max") double max) {

        List<Fattura> fatture = fatturaService.findByImportoBetween(min, max);

        if (fatture.isEmpty()) {
            throw new NotFoundException("Nessuna fattura trovata con importi tra " + min + " e " + max);
        }

        return fatture;
    }



        //delete
        @DeleteMapping("/{id}")
        public void deleteFattura(@PathVariable UUID id) {
            fatturaService.deleteFattura(id);
        }

    }

