package eth.craig.alert0x.service.alert;

import eth.craig.alert0x.model.alert.Alert;
import eth.craig.alert0x.model.alert.AlertContext;

public interface AlertService {

    void sendAlert(Alert alert, AlertContext context);
}
