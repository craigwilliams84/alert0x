package eth.craig.alert0x.spec.verifier;

import eth.craig.alert0x.model.ethereum.InternalTransaction;
import eth.craig.alert0x.model.ethereum.TransactionReceipt;
import eth.craig.alert0x.model.event.ContractEvent;
import eth.craig.alert0x.service.BlockchainService;
import eth.craig.alert0x.spec.EventEmitted;

import java.util.List;

public class EventInternalTransactionFromVerifier implements CriterionVerifier<EventEmitted, ContractEvent> {

    private BlockchainService blockchainService;

    public EventInternalTransactionFromVerifier(BlockchainService blockchainService) {
        this.blockchainService = blockchainService;
    }

    @Override
    public boolean isMatching(EventEmitted alert, ContractEvent event) {

        if (alert.getInternalTransactionSentFrom() == null || alert.getInternalTransactionSentFrom().isEmpty()) {
            return true;
        }

        final List<InternalTransaction> internalTransactions =
                blockchainService.getInternalTransactions(event.getTransactionHash());

        if (internalTransactions == null || internalTransactions.isEmpty()) {
            return false;
        }

        return internalTransactions
                .stream()
                .anyMatch(tx -> alert.getInternalTransactionSentFrom()
                        .stream()
                        .anyMatch(fromAddress -> tx.getFrom().equalsIgnoreCase(fromAddress)));
    }
}
