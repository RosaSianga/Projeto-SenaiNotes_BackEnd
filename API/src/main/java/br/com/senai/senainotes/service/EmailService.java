package br.com.senai.senainotes.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String origem;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmail(String destino, String novaSenha) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(origem);
        message.setTo(destino);
        message.setSubject("Sua Nova Senha - Senai Notes");
        message.setText("Olá,\n\nSua senha foi redefinida com sucesso.\n\n" +
                "Sua nova senha temporária é: " + novaSenha +
                "\n\nRecomendamos que você altere esta senha após o primeiro login.");

        mailSender.send(message);
    }
}
