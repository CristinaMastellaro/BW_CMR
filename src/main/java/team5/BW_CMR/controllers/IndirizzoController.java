package team5.BW_CMR.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team5.BW_CMR.entities.Indirizzo;
import team5.BW_CMR.payloads.IndirizzoDTO;

@RestController
@RequestMapping("/address")
public class IndirizzoController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Indirizzo saveAddress(IndirizzoDTO newAddress) {

    }

}
