package eth.craig.alert0x.config;

import eth.craig.alert0x.integration.Alert0xBlockchainEventListener;
import eth.craig.alert0x.repository.eventeum.DoNothingRepository;
import net.consensys.eventeum.chain.service.BlockchainService;
import net.consensys.eventeum.dto.event.ContractEventDetails;
import net.consensys.eventeum.factory.EventStoreFactory;
import net.consensys.eventeum.integration.broadcast.blockchain.BlockchainEventBroadcaster;
import net.consensys.eventeum.integration.broadcast.blockchain.ListenerInvokingBlockchainEventBroadcaster;
import net.consensys.eventeum.integration.eventstore.SaveableEventStore;
import net.consensys.eventeum.model.LatestBlock;
import net.consensys.eventeum.model.TransactionMonitoringSpec;
import net.consensys.eventeum.repository.factory.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.web3j.protocol.Web3jService;

import java.math.BigInteger;
import java.util.Optional;

@Configuration
public class EventeumConfiguration {

    @Bean
    public BlockchainEventBroadcaster listenerBroadcaster(Alert0xBlockchainEventListener listener) {
        return new ListenerInvokingBlockchainEventBroadcaster(listener);
    }

    @Bean
    public TransactionMonitoringSpecRepositoryFactory transactionMonitoringSpecRepositoryFactory() {
        return () -> new DoNothingRepository<>();
    }

    @Bean
    public ContractEventFilterRepositoryFactory contractEventFilterRepositoryFactory() {
        return () -> new DoNothingRepository<>();
    }

    @Bean
    public EventFilterSyncStatusRepositoryFactory eventFilterSyncStatusRepositoryFactory() {
        return () -> new DoNothingRepository<>();
    }

    @Bean
    public EventStoreFactory eventStoreFactory(BlockchainService blockchainService) {
        return () -> new SaveableEventStore() {
            @Override
            public void save(ContractEventDetails contractEventDetails) {

            }

            @Override
            public void save(LatestBlock latestBlock) {

            }

            @Override
            public Page<ContractEventDetails> getContractEventsForSignature(String eventSignature, String contractAddress, PageRequest pagination) {
                return null;
            }

            @Override
            public Optional<LatestBlock> getLatestBlockForNode(String nodeName) {
                final BigInteger currentBlockNumber =  blockchainService.getCurrentBlockNumber();

                final LatestBlock latestBlock = new LatestBlock();
                latestBlock.setNumber(currentBlockNumber);

                return Optional.of(latestBlock);
            }

            @Override
            public boolean isPagingZeroIndexed() {
                return false;
            }
        };
    }
}
