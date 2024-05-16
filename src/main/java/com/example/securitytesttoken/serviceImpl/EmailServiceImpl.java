package com.example.securitytesttoken.serviceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:email.properties")
public class EmailServiceImpl {

	private final JavaMailSender javaMailSender;
	
	@Value("${user.email.addr}")
	private String emailAddr;
	
	private MimeMessage createMessage(String subject, String msg) throws Exception{
        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, emailAddr);
        message.setSubject(subject);
        String content = "<h1>현재 접속한 사용자 정보</h1>";
        content += "<div>" + msg + "</div>";
        message.setText(content, "utf-8", "html");
        message.setFrom(new InternetAddress(emailAddr, "Spring-Security-ADMIN"));// 보내는 사람
        return  message;
    }

    public void sendMail(String subject, String msg) throws Exception{
        try{
            MimeMessage mimeMessage = createMessage(subject, msg);
            javaMailSender.send(mimeMessage);
        }catch (MailException mailException){
            mailException.printStackTrace();
            throw   new IllegalAccessException();
        }
    }
    
}
