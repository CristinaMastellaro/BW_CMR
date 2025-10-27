package team5.BW_CMR.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team5.BW_CMR.entities.Indirizzo;
import team5.BW_CMR.payloads.IndirizzoDTO;
import team5.BW_CMR.services.IndirizzoService;

@RestController
@RequestMapping("/address")
public class IndirizzoController {
    @Autowired
    private IndirizzoService iServ;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Indirizzo saveAddress(@RequestBody @Validated IndirizzoDTO newAddress, BindingResult validation) {
        if (validation.hasErrors())
            throw new
        return iServ.saveIndirizzo(newAddress);
    }

}
