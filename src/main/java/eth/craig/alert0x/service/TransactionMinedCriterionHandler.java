package eth.craig.alert0x.service;

import eth.craig.alert0x.model.event.TransactionStatus;
import eth.craig.alert0x.model.monitor.TransactionIdentifierType;
import eth.craig.alert0x.model.monitor.TransactionMonitor;
import eth.craig.alert0x.spec.Criterion;
import eth.craig.alert0x.spec.TransactionMined;
import eth.craig.alert0x.util.JSON;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class TransactionMinedCriterionHandler implements CriterionHandler {

    private BlockchainMonitorService blockchainMonitorService;

    @Override
    public boolean isSupported(Criterion criterion) {
        return criterion instanceof TransactionMined;
    }

    @Override
    public void handle(String id, Criterion criterion) {
        final TransactionMined txMined = (TransactionMined) criterion;

        final TransactionMonitor monitor = buildMonitor(id, txMined);
        log.info("Registering transaction monitor: {}", JSON.stringify(monitor));
        blockchainMonitorService.monitorTransactions(monitor);
    }

    private TransactionMonitor buildMonitor(String id, TransactionMined txMined) {
        final TransactionMonitor.TransactionMonitorBuilder monitorBuilder = TransactionMonitor.builder();

        if (txMined.getFrom() != null) {
            monitorBuilder
                    .type(TransactionIdentifierType.FROM_ADDRESS)
                    .identifierValue(txMined.getFrom());
        } else if (txMined.getTo() != null) {
            monitorBuilder
                    .type(TransactionIdentifierType.TO_ADDRESS)
                    .identifierValue(txMined.getTo());
        }

        return monitorBuilder
                .id(id)
                .status(TransactionStatus.CONFIRMED)
                .build();
    }
}
