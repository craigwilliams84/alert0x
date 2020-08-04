package eth.craig.alert0x.model.factory;

import eth.craig.alert0x.model.event.TransactionEvent;
import eth.craig.alert0x.model.event.TransactionStatus;
import net.consensys.eventeum.dto.transaction.TransactionDetails;
import org.springframework.stereotype.Component;
import org.web3j.utils.Numeric;

@Component
public class TransactionEventFactory {

    public TransactionEvent build(TransactionDetails transactionDetails) {
        return TransactionEvent
                .builder()
                .transactionHash(transactionDetails.getHash())
                .nonce(Numeric.decodeQuantity(transactionDetails.getNonce()))
                .blockHash(transactionDetails.getBlockHash())
                .blockNumber(Numeric.decodeQuantity(transactionDetails.getBlockNumber()))
                .transactionIndex(Numeric.decodeQuantity(transactionDetails.getTransactionIndex()))
                .from(transactionDetails.getFrom())
                .to(transactionDetails.getTo())
                .value(Numeric.decodeQuantity(transactionDetails.getValue()))
                .nodeName(transactionDetails.getNodeName())
                .contractAddress(transactionDetails.getContractAddress())
                .data(transactionDetails.getInput())
                .revertReason(transactionDetails.getRevertReason())
                .timestamp(transactionDetails.getTimestamp())
                .status(TransactionStatus.valueOf(transactionDetails.getStatus().name()))
                .monitorId(transactionDetails.getMonitorId())
                .build();
    }
}
