package team5.BW_CMR.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import team5.BW_CMR.entities.Provincia;
import team5.BW_CMR.services.ProvinciaService;

@RestController
@RequestMapping("/provincia")
public class ProvinciaController {
    @Autowired
    private ProvinciaService pServ;

    @GetMapping
    public Page<Provincia> findAllProvince(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, @RequestParam(defaultValue = "regione") String sortBy) {
        return pServ.findAllProvince(page, size, sortBy);
    }

    @GetMapping("/{provinciaId}")
    public Provincia findProvinciaById(@PathVariable long provinciaId) {
        return pServ.findProvinciaById(provinciaId);
    }
}
