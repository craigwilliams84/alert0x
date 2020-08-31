package eth.craig.alert0x.service;

import eth.craig.alert0x.model.event.TransactionStatus;
import eth.craig.alert0x.model.monitor.ContractEventMonitor;
import eth.craig.alert0x.model.monitor.TransactionMonitor;
import eth.craig.alert0x.util.JSON;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.consensys.eventeum.dto.event.filter.ContractEventFilter;
import net.consensys.eventeum.dto.event.filter.ContractEventSpecification;
import net.consensys.eventeum.dto.event.filter.ParameterDefinition;
import net.consensys.eventeum.dto.event.filter.ParameterType;
import net.consensys.eventeum.model.TransactionMonitoringSpec;
import net.consensys.eventeum.service.SubscriptionService;
import net.consensys.eventeum.service.TransactionMonitoringService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class EventeumBlockchainService implements BlockchainMonitorService {

    private SubscriptionService eventeumSubscriptionService;
    private TransactionMonitoringService eventeumTransactionService;

    @Override
    public void monitorTransactions(TransactionMonitor transactionMonitor) {
        final TransactionMonitoringSpec eventeumSpec = new TransactionMonitoringSpec();
        eventeumSpec.setId(transactionMonitor.getId());
        //TODO node name
        eventeumSpec.setNodeName("default");
        eventeumSpec.setType(transactionMonitor.getType().toString());
        eventeumSpec.setTransactionIdentifierValue(transactionMonitor.getIdentifierValue());
        eventeumSpec.setStatuses(transactionMonitor
                .getStatuses()
                .stream()
                .map(status -> convert(status))
                .collect(Collectors.toList()));

        eventeumTransactionService.registerTransactionsToMonitor(eventeumSpec);
    }

    @Override
    public void monitorEvents(ContractEventMonitor contractEventMonitor) {
        log.info("Registering eventeum contract event filter: {}", JSON.stringify(contractEventMonitor));
        eventeumSubscriptionService.registerContractEventFilter(convert(contractEventMonitor), true);
    }

    private net.consensys.eventeum.dto.transaction.TransactionStatus convert(TransactionStatus status) {
        return net.consensys.eventeum.dto.transaction.TransactionStatus.valueOf(status.name());
    }

    private ContractEventFilter convert(ContractEventMonitor monitor) {
        final String spec = monitor.getSpecificationDefinition();

        final String[] splitOnOpenParenthesis = spec.split("\\(");

        final String eventName = splitOnOpenParenthesis[0].trim();
        final String[] args = splitOnOpenParenthesis[1].replace("\\)", "").split(",");

        final List<ParameterDefinition> indexedParameters = new ArrayList<>();
        final List<ParameterDefinition> nonIndexedParameters  = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            String arg = args[i].trim();

            boolean isIndexed = false;
            if (arg.startsWith("indexed ")) {
                isIndexed = true;
                arg = arg.substring(8);
            }

            final String type = arg.substring(0, arg.indexOf(" "));
            final String name = arg.substring(arg.indexOf(" "));

            final ParameterDefinition definition = new ParameterDefinition(i, ParameterType.build(type.toUpperCase()));

            if (isIndexed) {
                indexedParameters.add(definition);
            } else {
                nonIndexedParameters.add(definition);
            }
        }

        final ContractEventSpecification eventSpecification = new ContractEventSpecification();
        eventSpecification.setEventName(eventName);
        eventSpecification.setNonIndexedParameterDefinitions(nonIndexedParameters);
        eventSpecification.setIndexedParameterDefinitions(indexedParameters);

        final ContractEventFilter filter = new ContractEventFilter();
        filter.setId(monitor.getId());
        filter.setContractAddress(monitor.getContractAddress());
        filter.setEventSpecification(eventSpecification);

        return filter;
    }
}
