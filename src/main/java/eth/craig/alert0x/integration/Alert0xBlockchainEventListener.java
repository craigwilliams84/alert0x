package eth.craig.alert0x.integration;

import eth.craig.alert0x.model.factory.TransactionEventFactory;
import eth.craig.alert0x.util.JSON;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.consensys.eventeum.dto.block.BlockDetails;
import net.consensys.eventeum.dto.event.ContractEventDetails;
import net.consensys.eventeum.dto.transaction.TransactionDetails;
import net.consensys.eventeum.integration.broadcast.blockchain.ListenerInvokingBlockchainEventBroadcaster.OnBlockchainEventListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
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
        log.info("Received transaction: {}", JSON.stringify(transactionDetails));
        applicationEventPublisher.publishEvent(transactionEventFactory.build(transactionDetails));
    }
}
