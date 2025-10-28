package team5.BW_CMR.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team5.BW_CMR.entities.Indirizzo;
import team5.BW_CMR.exceptions.ValidationException;
import team5.BW_CMR.payloads.IndirizzoDTO;
import team5.BW_CMR.services.IndirizzoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/address")
public class IndirizzoController {
    @Autowired
    private IndirizzoService iServ;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Indirizzo saveAddress(@RequestBody @Validated IndirizzoDTO newAddress, BindingResult validation) {
        if (validation.hasErrors())
            throw new ValidationException(validation.getFieldErrors().stream().map(fL -> fL.getDefaultMessage()).toList());
        return iServ.saveIndirizzo(newAddress);
    }

    @GetMapping("/{indirizzoId}")
    public Indirizzo findIndirizzoById(@PathVariable UUID indirizzoId) {
        return iServ.findIndirizzoById(indirizzoId);
    }

    @GetMapping
    public List<Indirizzo> findAllAddresses() {
        return iServ.findAllAddresses();
    }

    @DeleteMapping("/{indirizzoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIndirizzoById(@PathVariable UUID indirizzoId) {
        iServ.deleteIndirizzo(indirizzoId);
    }

}
