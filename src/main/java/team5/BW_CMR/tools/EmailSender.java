package team5.BW_CMR.tools;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import team5.BW_CMR.entities.Utente;

@Component
public class EmailSender {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRegistrationEmail(Utente utente) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(utente.getEmail());
        message.setSubject("Registrazione completata");
        message.setText("Benvenuto in Epic energia");
        mailSender.send(message);
        System.out.println("email inviata");
    }

}