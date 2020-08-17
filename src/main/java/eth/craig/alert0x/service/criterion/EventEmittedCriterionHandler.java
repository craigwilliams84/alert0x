package eth.craig.alert0x.service.criterion;

import eth.craig.alert0x.model.monitor.ContractEventMonitor;
import eth.craig.alert0x.model.monitor.TransactionMonitor;
import eth.craig.alert0x.service.BlockchainMonitorService;
import eth.craig.alert0x.spec.Criterion;
import eth.craig.alert0x.spec.EventEmitted;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EventEmittedCriterionHandler implements CriterionHandler {

    private BlockchainMonitorService blockchainMonitorService;

    @Override
    public boolean isSupported(Criterion criterion) {
        return criterion instanceof EventEmitted;
    }

    @Override
    public void register(String id, Criterion criterion) {
        final EventEmitted eventEmitted = (EventEmitted) criterion;

        blockchainMonitorService.monitorEvents(ContractEventMonitor
                .builder()
                .id(id)
                .contractAddress(eventEmitted.getFromContract())
                .specificationDefinition(eventEmitted.getSpec())
                .build());
    }
}
