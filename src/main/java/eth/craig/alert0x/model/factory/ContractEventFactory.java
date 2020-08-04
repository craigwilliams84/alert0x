package eth.craig.alert0x.model.factory;

import eth.craig.alert0x.model.event.*;
import net.consensys.eventeum.dto.event.ContractEventDetails;
import net.consensys.eventeum.dto.transaction.TransactionDetails;
import org.springframework.stereotype.Component;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContractEventFactory {

    public ContractEvent build(ContractEventDetails contractEventDetails) {

        return ContractEvent
                .builder()
                .name(contractEventDetails.getName())
                .monitorId(contractEventDetails.getFilterId())
                .nodeName(contractEventDetails.getNodeName())
                .indexedParameters(convert(contractEventDetails.getIndexedParameters()))
                .nonIndexedParameters(convert(contractEventDetails.getNonIndexedParameters()))
                .transactionHash(contractEventDetails.getTransactionHash())
                .logIndex(contractEventDetails.getLogIndex())
                .blockNumber(contractEventDetails.getBlockNumber())
                .address(contractEventDetails.getAddress())
                .status(ContractEventStatus.valueOf(contractEventDetails.getStatus().name()))
                .eventSpecificationSignature(contractEventDetails.getEventSpecificationSignature())
                .timestamp(contractEventDetails.getTimestamp())
                .build();
    }

    private List<EventParameter> convert(List<net.consensys.eventeum.dto.event.parameter.EventParameter> eventeumParms) {
        return eventeumParms
                .stream()
                .map(param -> EventParameter
                        .builder()
                        .type(param.getType())
                        .value(param.getValueString())
                        .build())
                .collect(Collectors.toList());
    }
}
