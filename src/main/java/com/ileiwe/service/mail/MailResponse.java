package com.ileiwe.service.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
public class MailResponse {
    private boolean isSuccessful;
}
