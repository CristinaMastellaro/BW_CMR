package team5.BW_CMR.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team5.BW_CMR.entities.Cliente;
import team5.BW_CMR.entities.Fattura;
import team5.BW_CMR.payloads.FatturaDTO;
import team5.BW_CMR.repositories.ClienteRepository;
import team5.BW_CMR.services.FatturaService;

@RestController
@RequestMapping("/api/fatture")
public class FatturaController {
    @Autowired
    private FatturaService fatturaService;

    @Autowired
    private ClienteRepository clienteRepository;

    //metodo post  http://localhost:8888/api/fatture

//    @PostMapping
//    public Fattura create(@RequestBody @Valid FatturaDTO dto){
//        Cliente cliente=clienteRepository.findById(dto.clienteID()).orElseThrow(()-> new NotFoundExù
//
//    }


}
