package eth.craig.alert0x.service;

import eth.craig.alert0x.model.EmailMessage;

public interface EmailService {

    void sendEmail(EmailMessage emailMessage);
}
