package team5.BW_CMR.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Configuration
public class configClass {

    @Bean
    public List<List<String>> csvComuni() {
        String fileName = "src/main/java/team5/BW_CMR/csv/comuni-italiani.csv";
        File file = new File(fileName);

        List<List<String>> listaComuni = new ArrayList<>();
        Scanner inputStream;

        try {
            inputStream = new Scanner(file);
            System.out.println("Input stream " + inputStream);

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
}
