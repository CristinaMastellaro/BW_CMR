package team5.BW_CMR.config;

import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Configuration
public class configClass {

    //    @Bean(name = "csvComuni")
//    @Scope("prototype")
    public List<List<String>> csvComuni() {
        String fileName = "src/main/java/team5/BW_CMR/csv/comuni-italiani.csv";
        File file = new File(fileName);

        List<List<String>> listaComuni = new ArrayList<>();
        Scanner inputStream;

        try {
            inputStream = new Scanner(file);

            while (inputStream.hasNext()) {
                String singolaLineaComune = inputStream.nextLine();
                String[] comuni = singolaLineaComune.split(";");
                listaComuni.add(Arrays.asList(comuni));
            }

            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        listaComuni.removeFirst();
        return listaComuni;
    }

    //    @Bean(name = "csvProvince")
//    @Scope("prototype")
    public List<List<String>> csvProvince() {
        String fileName = "src/main/java/team5/BW_CMR/csv/province-italiane.csv";
        File file = new File(fileName);

        List<List<String>> listaProvince = new ArrayList<>();
        Scanner inputStream;

        try {
            inputStream = new Scanner(file);

            while (inputStream.hasNext()) {
                String singolaLineaComune = inputStream.nextLine();
                String[] province = singolaLineaComune.split(";");
                listaProvince.add(Arrays.asList(province));
            }

            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        listaProvince.removeFirst();
        return listaProvince;
    }
}
