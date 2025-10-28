package team5.BW_CMR.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import team5.BW_CMR.entities.Comune;
import team5.BW_CMR.services.ComuneService;

@RestController
@RequestMapping("/comune")
public class ComuneController {
    @Autowired
    private ComuneService cServ;

    @GetMapping
    public Page<Comune> findAllComune(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return cServ.findAllComuni(page, size, sortBy);
    }

    @GetMapping("/{comuneId}")
    public Comune findComuneById(@PathVariable long comuneId) {
        return cServ.findComuneById(comuneId);
    }
}
