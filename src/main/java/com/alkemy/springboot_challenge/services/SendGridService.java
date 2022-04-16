package com.alkemy.springboot_challenge.services;

import com.alkemy.springboot_challenge.utils.SendGridMail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SendGridService {

    SendGrid sg = new SendGrid("APIKEY");

    public void sendRequest(SendGridMail sendGridMail) throws IOException {

        Mail mail = new Mail(sendGridMail.getFrom(), sendGridMail.getSubject(), sendGridMail.getTo(), sendGridMail.getContent());
        Request request = new Request();

        try{

            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

        } catch(Exception e){
            throw new IOException("Mail not sent: " + e);
        }
    }
}
