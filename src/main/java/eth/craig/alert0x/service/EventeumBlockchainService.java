package eth.craig.alert0x.service;

import eth.craig.alert0x.model.event.TransactionStatus;
import eth.craig.alert0x.model.monitor.TransactionMonitor;
import net.consensys.eventeum.model.TransactionMonitoringSpec;
import net.consensys.eventeum.service.TransactionMonitoringService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class EventeumBlockchainService implements BlockchainMonitorService {

    private TransactionMonitoringService eventeumTransactionService;

    public EventeumBlockchainService(TransactionMonitoringService eventeumTransactionService) {
        this.eventeumTransactionService = eventeumTransactionService;
    }

    @Override
    public void monitorTransactions(TransactionMonitor transactionMonitor) {
        final TransactionMonitoringSpec eventeumSpec = new TransactionMonitoringSpec();
        eventeumSpec.setId(transactionMonitor.getId());
        //TODO node name
        eventeumSpec.setNodeName("default");
        eventeumSpec.setType(transactionMonitor.getType().toString());
        eventeumSpec.setTransactionIdentifierValue(transactionMonitor.getIdentifierValue());
        eventeumSpec.setStatuses(Collections.singletonList(convert(transactionMonitor.getStatus())));

        eventeumTransactionService.registerTransactionsToMonitor(eventeumSpec);
    }

    private net.consensys.eventeum.dto.transaction.TransactionStatus convert(TransactionStatus status) {
        return net.consensys.eventeum.dto.transaction.TransactionStatus.valueOf(status.name());
    }
}
