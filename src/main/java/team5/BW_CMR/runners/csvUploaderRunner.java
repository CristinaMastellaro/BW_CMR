package team5.BW_CMR.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import team5.BW_CMR.entities.Comune;
import team5.BW_CMR.entities.Provincia;
import team5.BW_CMR.services.ComuneService;
import team5.BW_CMR.services.ProvinciaService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
        // Popoliamo la tabella "Province" del db
        if (pServ.findAllProvince().isEmpty()) {
//            List<List<String>> province = ctx.getBean("csvProvince", List.class);
            String fileName = "src/main/java/team5/BW_CMR/csv/province-italiane.csv";
            File file = new File(fileName);

//            List<List<String>> listaProvince = new ArrayList<>();
            Scanner inputStream;

            try {
                inputStream = new Scanner(file);
                boolean start = true;

                while (inputStream.hasNext()) {

                    String singolaLineaComune = inputStream.nextLine();
                    if (start) {
                        start = false;
                    } else {
//                    String[] province = Arrays.asList(singolaLineaComune.split(";"));
                        List<String> province = Arrays.asList(singolaLineaComune.split(";"));
//                    listaProvince.add(Arrays.asList(province));
                        Provincia nuovaProvincia = new Provincia(province.getFirst(), province.get(1), province.getLast());
                        pServ.saveProvincia(nuovaProvincia);
                    }
                }

                inputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
//            listaProvince.removeFirst();

//            for (List<String> provincia : province) {
//                Provincia nuovaProvincia = new Provincia(provincia.getFirst(), provincia.get(1), provincia.getLast());
//                pServ.saveProvincia(nuovaProvincia);
//            }
            Provincia VCO = new Provincia("VB", "Verbano-Cusio-Ossola", "Piemonte");
            Provincia bolzano = new Provincia("BZ", "Bolzano/Bozen", "Trentino Alto Adige");
            Provincia valleAosta = new Provincia("AO", "Valle d'Aosta/Vallée d'Aoste", "Valle d'Aosta/Vallée d'Aoste");
            pServ.saveProvincia(VCO);
            pServ.saveProvincia(bolzano);
            pServ.saveProvincia(valleAosta);
            System.out.println("Province salvate!");
        }

        // Popoliamo la tabella "Comuni" del db
        if (cServ.findAllComuni().isEmpty()) {
//            List<List<String>> comuni = ctx.getBean("csvComuni", List.class);
            String fileName = "src/main/java/team5/BW_CMR/csv/comuni-italiani.csv";
            File file = new File(fileName);

//            List<List<String>> listaComuni = new ArrayList<>();
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
//                        if (comune.getLast().equals("Monza e della Brianza"))
//                            provincia = pServ.findProvinciaByNome("Monza-Brianza");
//                        else if (comune.getLast().equals("La Spezia"))
//                            provincia = pServ.findProvinciaByNome("La-Spezia");
//                        else if (comune.getLast().equals("Forlì-Cesena"))
//                            provincia = pServ.findProvinciaByNome("Forli-Cesena");
//                        else if (comune.getLast().equals("Sud Sardegna"))
//                            provincia = pServ.findProvinciaByNome("Medio Campidano");
//                        else if (comune.getLast().equals("Vibo Valentia"))
//                            provincia = pServ.findProvinciaByNome("Vibo-Valentia");
//                        else if (comune.getLast().equals("Reggio Calabria"))
//                            provincia = pServ.findProvinciaByNome("Reggio-Calabria");
//                        else if (comune.getLast().equals("Ascoli Piceno"))
//                            provincia = pServ.findProvinciaByNome("Ascoli-Piceno");
//                        else if (comune.getLast().equals("Pesaro e Urbino"))
//                            provincia = pServ.findProvinciaByNome("Pesaro-Urbino");
//                        else if (comune.getLast().equals("Reggio nell'Emilia"))
//                            provincia = pServ.findProvinciaByNome("Reggio-Emilia");
//                        else provincia = pServ.findProvinciaByNome(comune.getLast());

                        Comune nuovoComune;
                        if (comune.get(1).equals("#RIF!")) {
                            num += 1;
                            nuovoComune = new Comune(Integer.parseInt(comune.getFirst()), num, comune.get(2), provincia);
                        } else
                            nuovoComune = new Comune(Integer.parseInt(comune.getFirst()), Integer.parseInt(comune.get(1)), comune.get(2), provincia);
                        cServ.saveComune(nuovoComune);
//                    listaComuni.add(Arrays.asList(comuni));}

                    }
                }

                inputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
//            listaComuni.removeFirst();
//            int num = 0;
//            for (List<String> comune : comuni) {
//                Provincia provincia;
//                if (comune.getLast().equals("Monza e della Brianza"))
//                    provincia = pServ.findProvinciaByNome("Monza-Brianza");
//                else if (comune.getLast().equals("La Spezia")) provincia = pServ.findProvinciaByNome("La-Spezia");
//                else if (comune.getLast().equals("Forlì-Cesena")) provincia = pServ.findProvinciaByNome("Forli-Cesena");
//                else if (comune.getLast().equals("Sud Sardegna"))
//                    provincia = pServ.findProvinciaByNome("Medio Campidano");
//                else if (comune.getLast().equals("Vibo Valentia"))
//                    provincia = pServ.findProvinciaByNome("Vibo-Valentia");
//                else if (comune.getLast().equals("Reggio Calabria"))
//                    provincia = pServ.findProvinciaByNome("Reggio-Calabria");
//                else if (comune.getLast().equals("Ascoli Piceno"))
//                    provincia = pServ.findProvinciaByNome("Ascoli-Piceno");
//                else if (comune.getLast().equals("Pesaro e Urbino"))
//                    provincia = pServ.findProvinciaByNome("Pesaro-Urbino");
//                else if (comune.getLast().equals("Reggio nell'Emilia"))
//                    provincia = pServ.findProvinciaByNome("Reggio-Emilia");
//                else provincia = pServ.findProvinciaByNome(comune.getLast());
//
//                Comune nuovoComune;
//                if (comune.get(1).equals("#RIF!")) {
//                    num += 1;
//                    nuovoComune = new Comune(Integer.parseInt(comune.getFirst()), num, comune.get(2), provincia);
//                } else
//                    nuovoComune = new Comune(Integer.parseInt(comune.getFirst()), Integer.parseInt(comune.get(1)), comune.get(2), provincia);
//                cServ.saveComune(nuovoComune);
//            }
            System.out.println("DB UPLOADED!");
        }

    }
}
