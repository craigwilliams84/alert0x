package eth.craig.alert0x.service.alert;

import eth.craig.alert0x.model.alert.Alert;
import eth.craig.alert0x.model.alert.AlertContext;
import eth.craig.alert0x.model.alert.HttpPost;
import org.springframework.http.MediaType;

public interface HttpPostBodyFactory {

    String getBody(HttpPost postAlert, AlertContext context);

    MediaType getContentType(HttpPost postAlert, AlertContext context);
}
