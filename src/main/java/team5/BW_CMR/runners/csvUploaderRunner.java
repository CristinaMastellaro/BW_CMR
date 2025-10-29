package team5.BW_CMR.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import team5.BW_CMR.entities.Comune;
import team5.BW_CMR.entities.Provincia;
import team5.BW_CMR.entities.Ruolo;
import team5.BW_CMR.entities.Utente;
import team5.BW_CMR.payloads.RuoloDTO;
import team5.BW_CMR.services.ComuneService;
import team5.BW_CMR.services.ProvinciaService;
import team5.BW_CMR.services.RuoloService;
import team5.BW_CMR.services.UtenteService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Component
public class csvUploaderRunner implements CommandLineRunner {
    @Autowired
    private ComuneService cServ;
    @Autowired
    private ProvinciaService pServ;
    @Autowired
    private RuoloService ruoloService;
    @Autowired
    private UtenteService utenteService;

    @Override
    public void run(String... args) throws Exception {

        // Popoliamo la tabella "Province" del db
        if (pServ.findAllProvince(0, 20, "regione").isEmpty()) {
            String fileName = "src/main/java/team5/BW_CMR/csv/province-italiane.csv";
            File file = new File(fileName);

            Scanner inputStream;
            try {
                inputStream = new Scanner(file);
                boolean start = true;

                while (inputStream.hasNext()) {
                    String singolaLineaComune = inputStream.nextLine();
                    if (start) {
                        start = false;
                    } else {
                        List<String> province = Arrays.asList(singolaLineaComune.split(";"));
                        Provincia nuovaProvincia = new Provincia(province.getFirst(), province.get(1), province.getLast());
                        pServ.saveProvincia(nuovaProvincia);
                    }
                }
                inputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Provincia VCO = new Provincia("VB", "Verbano-Cusio-Ossola", "Piemonte");
            Provincia bolzano = new Provincia("BZ", "Bolzano/Bozen", "Trentino Alto Adige");
            Provincia valleAosta = new Provincia("AO", "Valle d'Aosta/Vallée d'Aoste", "Valle d'Aosta/Vallée d'Aoste");
            pServ.saveProvincia(VCO);
            pServ.saveProvincia(bolzano);
            pServ.saveProvincia(valleAosta);
            System.out.println("Province salvate!");
        }

        // Popoliamo la tabella "Comuni" del db
        if (cServ.findAllComuni(0, 30, "id").isEmpty()) {
            String fileName = "src/main/java/team5/BW_CMR/csv/comuni-italiani.csv";
            File file = new File(fileName);
            Scanner inputStream;

            try {
                inputStream = new Scanner(file);
                boolean start = false;

                int num = 0;
                while (inputStream.hasNext()) {

                    String singolaLineaComune = inputStream.nextLine();
                    if (!start) {
                        start = true;
                    } else {
                        List<String> comune = Arrays.asList(singolaLineaComune.split(";"));
                        Provincia provincia = switch (comune.getLast()) {
                            case "Monza e della Brianza" -> pServ.findProvinciaByNome("Monza-Brianza");
                            case "La Spezia" -> pServ.findProvinciaByNome("La-Spezia");
                            case "Forlì-Cesena" -> pServ.findProvinciaByNome("Forli-Cesena");
                            case "Sud Sardegna" -> pServ.findProvinciaByNome("Medio Campidano");
                            case "Vibo Valentia" -> pServ.findProvinciaByNome("Vibo-Valentia");
                            case "Reggio Calabria" -> pServ.findProvinciaByNome("Reggio-Calabria");
                            case "Ascoli Piceno" -> pServ.findProvinciaByNome("Ascoli-Piceno");
                            case "Pesaro e Urbino" -> pServ.findProvinciaByNome("Pesaro-Urbino");
                            case "Reggio nell'Emilia" -> pServ.findProvinciaByNome("Reggio-Emilia");
                            default -> pServ.findProvinciaByNome(comune.getLast());
                        };
                        Comune nuovoComune;
                        if (comune.get(1).equals("#RIF!")) {
                            num += 1;
                            nuovoComune = new Comune(Integer.parseInt(comune.getFirst()), num, comune.get(2), provincia);
                        } else
                            nuovoComune = new Comune(Integer.parseInt(comune.getFirst()), Integer.parseInt(comune.get(1)), comune.get(2), provincia);
                        cServ.saveComune(nuovoComune);
                    }
                }
                inputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("DB UPLOADED!");
        }
        if (ruoloService.findAll().isEmpty()) {
            RuoloDTO user = new RuoloDTO("USER");
            RuoloDTO admin = new RuoloDTO("ADMIN");
            ruoloService.saveRuolo(user);
            ruoloService.saveRuolo(admin);
        }


    }
}
