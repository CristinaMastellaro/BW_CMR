package team5.BW_CMR.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team5.BW_CMR.entities.Cliente;
import team5.BW_CMR.entities.Fattura;
import team5.BW_CMR.exceptions.BadRequestException;
import team5.BW_CMR.exceptions.NotFoundException;
import team5.BW_CMR.exceptions.ValidationException;
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

    //metodo post  http://localhost:3001/api/fatture
    @PostMapping
    public Fattura create(@RequestBody @Validated FatturaDTO dto, BindingResult validationResult) {

        if (validationResult.hasErrors()) {throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        Cliente cliente = clienteRepository.findById(dto.clienteId()).orElseThrow(() -> new NotFoundException("cliente non trovato con id:" + dto.clienteId()));

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


    //Get tutte le fatture http://localhost:3001/api/fatture
    @GetMapping
    public List<Fattura> getAll() {
        return fatturaService.findAll();
    }


//get fattura per cliente http://localhost.3001/api/fattura/cliente/{id}

@GetMapping("/cliente/{clienteId}")
public List<Fattura> getByCliente(@PathVariable UUID clienteId){
        List<Fattura> result=fatturaService.findByCliente_Id(clienteId);
        if(result.isEmpty()) throw new NotFoundException("nessuna fattura trovata per cliente con id:" + clienteId);
        return result;
}

//get fattura per una data esatta http://localhost.3001/api/fatture/data/{data}

    @GetMapping("/data/{data}")
    public List<Fattura> getByData(@PathVariable String data){
        LocalDate parsedDate;
        try{
            parsedDate= LocalDate.parse(data);
        } catch (RuntimeException e) {
            throw new BadRequestException("Formato data non valido!");
        }
        List <Fattura> result= fatturaService.findByData(parsedDate);
        if(result.isEmpty())throw new NotFoundException("nessuna fattura per la data "+ data);
        return result;
    }

//get specification http://localhost:3001/api/fatture/filter?importoMin=1000 (x esempio)

    @GetMapping("/filter")
    public List<Fattura> filterFatture(
            @RequestParam(required = false)Double importoMin,
            @RequestParam(required = false)Double importoMax,
            @RequestParam(required = false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)LocalDate dataMin,
            @RequestParam(required = false)@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)LocalDate dataMax,
            @RequestParam(required = false)Long numero,
            @RequestParam(required = false)UUID clienteId
    ) {
        List<Fattura> result = fatturaService.filterFatture(importoMin, importoMax, dataMin, dataMax, numero, clienteId);
        if (result.isEmpty()) {
            throw new NotFoundException("Nessuna fattura trovata con questi criteri");
        }
        return result;

    }


        //delete http://localhost:3001/api/fatture/{id}
        @DeleteMapping("/{id}")
        public void deleteFattura(@PathVariable UUID id) {
            fatturaService.deleteFattura(id);
        }

    }

