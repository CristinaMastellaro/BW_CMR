package team5.BW_CMR.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Bean
    public String getPassword(@Value("${spring.datasource.password}") String password) {
        return password;
    }
}
