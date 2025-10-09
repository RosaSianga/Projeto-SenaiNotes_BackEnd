package br.com.senai.senainotes.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmail(String to, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Senai Notes - Redefinação de senha");
        message.setText("Olá,\n\nSua senha foi redefinida com sucesso.\n\n" +
                "Sua nova senha temporária é: " + newPassword +
                "\n\nRecomendamos que você altere esta senha após o primeiro login.");

        mailSender.send(message);
    }
}