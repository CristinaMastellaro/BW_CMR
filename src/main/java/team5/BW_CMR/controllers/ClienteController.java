package team5.BW_CMR.controllers;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team5.BW_CMR.entities.Cliente;
import team5.BW_CMR.exceptions.ValidationException;
import team5.BW_CMR.payloads.ClienteDTO;
import team5.BW_CMR.services.ClienteService;

import java.util.UUID;
@RestController
@RequestMapping("/api/clienti")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;


    //POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save(@RequestBody @Validated ClienteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        return clienteService.save(body);
    }
    // GET ALL
    @GetMapping
    public Page<Cliente> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return clienteService.findAll(page, size, sortBy);
    }

    // GET SINGLE
    @GetMapping("/{id}")
    public Cliente getById(@PathVariable UUID id) {
        return clienteService.findById(id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        clienteService.findByIdAndDelete(id);
    }
}
