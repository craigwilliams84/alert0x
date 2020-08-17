package eth.craig.alert0x.spec.verifier;

import eth.craig.alert0x.model.ethereum.TransactionReceipt;
import eth.craig.alert0x.model.event.ContractEvent;
import eth.craig.alert0x.service.BlockchainService;
import eth.craig.alert0x.spec.EventEmitted;

public class EventTransactionFromVerifier implements CriterionVerifier<EventEmitted, ContractEvent> {

    private BlockchainService blockchainService;

    public EventTransactionFromVerifier(BlockchainService blockchainService) {
        this.blockchainService = blockchainService;
    }

    @Override
    public boolean isMatching(EventEmitted alert, ContractEvent event) {

        if (alert.getTransactionSentFrom() == null || alert.getTransactionSentFrom().isEmpty()) {
            return true;
        }

        final TransactionReceipt txReceipt = blockchainService.getTransactionReceipt(event.getTransactionHash());

        for (String fromAddressToMatch : alert.getTransactionSentFrom()) {
            if (fromAddressToMatch.equalsIgnoreCase(txReceipt.getFrom())) {
                return true;
            }
        }

        return false;
    }
}
