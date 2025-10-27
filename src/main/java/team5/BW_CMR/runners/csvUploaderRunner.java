package team5.BW_CMR.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import team5.BW_CMR.entities.Comune;
import team5.BW_CMR.entities.Provincia;
import team5.BW_CMR.services.ComuneService;
import team5.BW_CMR.services.ProvinciaService;

import java.util.List;

// Utile più per capire come richiamare il contenuto del csv
@Component
public class csvUploaderRunner implements CommandLineRunner {
    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private ComuneService cServ;
    @Autowired
    private ProvinciaService pServ;

    @Override
    public void run(String... args) throws Exception {
        if (cServ.findAllComuni().isEmpty()) {
            List<List<String>> comuni = ctx.getBean("csvComuni", List.class);
            for (List<String> comune : comuni) {
                Comune nuovoComune = new Comune(Integer.parseInt(comune.getFirst()), Integer.parseInt(comune.get(1)), comune.get(2), comune.getLast());
                cServ.saveComune(nuovoComune);
            }
        }
        if (pServ.findAllProvince().isEmpty()) {
            List<List<String>> province = ctx.getBean("csvProvince", List.class);
            for (List<String> provincia : province) {
                Provincia nuovaProvincia = new Provincia(provincia.getFirst(), provincia.get(1), provincia.getLast());
                pServ.saveProvincia(nuovaProvincia);
            }
        }

    }
}
