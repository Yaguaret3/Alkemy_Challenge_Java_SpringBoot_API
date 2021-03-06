package com.alkemy.springboot_challenge.utils;

import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.Getter;

public class SendGridMail {

    @Getter
    private Email from = new Email("challenge@alkemy.com");
    @Getter
    private Email to = null;
    @Getter
    private String subject = "Registration to Challenge Alkemy successful";
    @Getter
    private Content content = new Content("text/plain",
            "Thank you for joining Challenge Alkemy. \nWe hope you have a great time with us!");

    public SendGridMail(String to){
        this.to = new Email(to);
    }
}
