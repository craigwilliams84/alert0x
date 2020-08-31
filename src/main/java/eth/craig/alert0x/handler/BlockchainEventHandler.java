package eth.craig.alert0x.handler;

import eth.craig.alert0x.model.alert.Alert;
import eth.craig.alert0x.model.alert.AlertContext;
import eth.craig.alert0x.model.event.ContractEvent;
import eth.craig.alert0x.model.event.TransactionEvent;
import eth.craig.alert0x.service.AlertSpecRegistrar;
import eth.craig.alert0x.service.alert.AlertService;
import eth.craig.alert0x.service.factory.AlertContextFactory;
import eth.craig.alert0x.spec.AlertSpec;
import eth.craig.alert0x.util.JSON;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class BlockchainEventHandler {

    private AlertSpecRegistrar alertSpecRegistrar;

    private AlertService alertService;

    private AlertContextFactory<TransactionEvent> transactionAlertContextFactory;

    private AlertContextFactory<ContractEvent> eventAlertContextFactory;

    @Async
    @EventListener
    public void handleTransactionEvent(TransactionEvent transactionEvent) {
        final AlertSpec alertSpec = alertSpecRegistrar.getRegisteredAlertSpecs().get(transactionEvent.getMonitorId());

        if (alertSpec.getCriterion().matches(transactionEvent)) {
            log.info("Transaction {} matches alert spec {}", transactionEvent.getTransactionHash(), alertSpec.getId());
            alertSpec.getAlerts().forEach(alert -> sendAlert(alert, transactionAlertContextFactory.build(transactionEvent)));
        }
    }

    @Async
    @EventListener
    public void handleContractEvent(ContractEvent contractEvent) {
        final AlertSpec alertSpec = alertSpecRegistrar.getRegisteredAlertSpecs().get(contractEvent.getMonitorId());

        if (alertSpec.getCriterion().matches(contractEvent)) {
            log.info("Contract event {} matches alert spec {}", contractEvent.getName(), alertSpec.getId());
            alertSpec.getAlerts().forEach(alert -> sendAlert(alert, eventAlertContextFactory.build(contractEvent)));
        }
    }

    private void sendAlert(Alert alert, AlertContext alertContext) {
        try {
            log.info("Sending alert {}", JSON.stringify(alert));
            alertService.sendAlert(alert, alertContext);
        } catch (Exception e) {
            log.error("Unable to send alert " + JSON.stringify(alert), e);
        }
    }
}
