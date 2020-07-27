package eth.craig.alert0x.service.alert;

import eth.craig.alert0x.model.EmailMessage;
import eth.craig.alert0x.model.alert.Alert;
import eth.craig.alert0x.model.alert.AlertContext;
import eth.craig.alert0x.model.alert.AlertType;
import eth.craig.alert0x.model.alert.Email;
import eth.craig.alert0x.service.EmailService;
import eth.craig.alert0x.template.TemplateEngine;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static eth.craig.alert0x.model.alert.AlertContextKeys.SPEC_ID;

@Component
@AllArgsConstructor
public class EmailAlertHandler implements AlertHandler {

    private EmailService emailService;

    private TemplateEngine templateEngine;

    @Override
    public boolean isSupported(Alert alert) {
        return alert.getType() == AlertType.EMAIL;
    }

    @Override
    public void handle(Alert alert, AlertContext context) {
        final Email emailAlert = (Email) alert;

        final EmailMessage emailMessage = EmailMessage
                .builder()
                .from(emailAlert.getFrom())
                .to(emailAlert.getTo())
                .subject(templateEngine.generateFromString(
                        context.getValue(SPEC_ID), emailAlert.getSubject(), context.getValues()))
                .contentText(templateEngine.generate(emailAlert.getBodyTemplate(), context.getValues()))
                .build();

        emailService.sendEmail(emailMessage);
    }
}
