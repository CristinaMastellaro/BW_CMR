package team5.BW_CMR.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Config {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public String getEncodedPassword(@Value("${pg.password}") String password) {
        return passwordEncoder.encode(password);
    }
}
