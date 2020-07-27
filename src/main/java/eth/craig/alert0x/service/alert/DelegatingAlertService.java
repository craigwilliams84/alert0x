package eth.craig.alert0x.service.alert;

import eth.craig.alert0x.model.alert.Alert;
import eth.craig.alert0x.model.alert.AlertContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DelegatingAlertService implements AlertService {

    private List<AlertHandler> delegates;

    @Override
    public void sendAlert(Alert alert, AlertContext context) {
        delegates
                .stream()
                .filter(handler -> handler.isSupported(alert))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Alert not supported"))
                .handle(alert, context);
    }
}
