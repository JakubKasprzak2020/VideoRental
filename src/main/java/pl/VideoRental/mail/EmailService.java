package pl.VideoRental.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailCfg emailCfg;

    private final String SET_FROM = "videorential@videorential.com";

    public void sendEmail(String setTo, EmailContent emailContent) {
        JavaMailSenderImpl mailSender = createMailSender();
        SimpleMailMessage mailMessage = createMailMessage(setTo, emailContent);
        mailSender.send(mailMessage);
    }

    private JavaMailSenderImpl createMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailCfg.getHost());
        mailSender.setPort(emailCfg.getPort());
        mailSender.setUsername(emailCfg.getUsername());
        mailSender.setPassword(emailCfg.getPassword());
        return mailSender;
    }

    private SimpleMailMessage createMailMessage(String setTo, EmailContent emailContent){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(SET_FROM);
        mailMessage.setTo(setTo);
        mailMessage.setSubject(emailContent.getSubject());
        mailMessage.setText(emailContent.getText());
        return mailMessage;
    }
}


