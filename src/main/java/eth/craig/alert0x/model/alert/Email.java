package eth.craig.alert0x.model.alert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Email implements Alert {

    private String from;

    private String to;

    private String subject;

    private String body;

    private String bodyTemplate;

    private String specId;

    @Override
    public AlertType getType() {
        return AlertType.EMAIL;
    }
}
