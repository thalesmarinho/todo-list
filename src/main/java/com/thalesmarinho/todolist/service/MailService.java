package com.thalesmarinho.todolist.service;

import com.thalesmarinho.todolist.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
@Service
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${api.mail.from}")
    private String from;
    @Value("${api.mail.sender}")
    private String sender;

    @Async
    public void sendActivationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String to = user.getEmail();
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "[[sender]].";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(from, sender);
        helper.setTo(to);
        helper.setSubject(subject);

        content = content
                .replace("[[name]]", user.getFirstName())
                .replace("[[sender]]", sender);

        String verifyURL = siteURL + "/api/users/verify?activationKey=" + user.getActivationKey();

        content = content.replace("[[URL]]", verifyURL);
        helper.setText(content, true);

        mailSender.send(message);
    }
}