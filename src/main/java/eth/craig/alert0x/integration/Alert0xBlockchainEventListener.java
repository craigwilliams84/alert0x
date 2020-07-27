package eth.craig.alert0x.integration;

import eth.craig.alert0x.model.factory.TransactionEventFactory;
import lombok.AllArgsConstructor;
import net.consensys.eventeum.dto.block.BlockDetails;
import net.consensys.eventeum.dto.event.ContractEventDetails;
import net.consensys.eventeum.dto.transaction.TransactionDetails;
import net.consensys.eventeum.integration.broadcast.blockchain.ListenerInvokingBlockchainEventBroadcaster.OnBlockchainEventListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Alert0xBlockchainEventListener implements OnBlockchainEventListener {

    private ApplicationEventPublisher applicationEventPublisher;

    private TransactionEventFactory transactionEventFactory;

    @Override
    public void onNewBlock(BlockDetails block) {

    }

    @Override
    public void onContractEvent(ContractEventDetails eventDetails) {

    }

    @Override
    public void onTransactionEvent(TransactionDetails transactionDetails) {
        applicationEventPublisher.publishEvent(transactionEventFactory.build(transactionDetails));
    }
}
