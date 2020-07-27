package eth.craig.alert0x.handler;

import eth.craig.alert0x.model.event.TransactionEvent;
import eth.craig.alert0x.service.AlertSpecRegistrar;
import eth.craig.alert0x.service.alert.AlertService;
import eth.craig.alert0x.service.factory.AlertContextFactory;
import eth.craig.alert0x.spec.AlertSpec;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BlockchainEventHandler {

    private AlertSpecRegistrar alertSpecRegistrar;

    private AlertService alertService;

    private AlertContextFactory<TransactionEvent> alertContextFactory;

    @Async
    @EventListener
    public void handleTransactionEvent(TransactionEvent transactionEvent) {
        final AlertSpec alertSpec = alertSpecRegistrar.getRegisteredAlertSpecs().get(transactionEvent.getMonitorId());

        if (alertSpec.getCriterion().matches(transactionEvent)) {
            alertSpec.getAlerts().forEach(alert -> alertService.sendAlert(
                    alert, alertContextFactory.build(transactionEvent)));
        }
    }
}
