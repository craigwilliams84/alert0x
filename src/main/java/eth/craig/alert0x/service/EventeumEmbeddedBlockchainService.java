package eth.craig.alert0x.service;

import eth.craig.alert0x.exception.Alert0xException;
import eth.craig.alert0x.exception.RateLimitReachedException;
import eth.craig.alert0x.model.ethereum.InternalTransaction;
import eth.craig.alert0x.model.ethereum.TransactionReceipt;
import eth.craig.alert0x.util.JSON;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class EventeumEmbeddedBlockchainService implements BlockchainService {

    private net.consensys.eventeum.chain.service.BlockchainService eventeumBlockchainService;

    private EtherscanClient etherscanClient;

    private RetryTemplate retryTemplate;

    private RetryTemplate etherscanRetryTemplate;

    public EventeumEmbeddedBlockchainService(net.consensys.eventeum.chain.service.BlockchainService eventeumBlockchainService,
                                             EtherscanClient etherscanClient,
                                             @Qualifier("retryTemplate") RetryTemplate retryTemplate,
                                             @Qualifier("etherscanRetryTemplate") RetryTemplate etherscanRetryTemplate) {
        this.eventeumBlockchainService = eventeumBlockchainService;
        this.etherscanClient = etherscanClient;
        this.retryTemplate = retryTemplate;
        this.etherscanRetryTemplate = etherscanRetryTemplate;
    }

    public TransactionReceipt getTransactionReceipt(String txId) {

        return retryTemplate.execute((context) -> {
            final net.consensys.eventeum.chain.service.domain.TransactionReceipt eventeumReceipt =
                    eventeumBlockchainService.getTransactionReceipt(txId);

            if (eventeumReceipt == null) {
                throw new RuntimeException("Null transaction receipt");
            }

            return TransactionReceipt
                    .builder()
                    .blockHash(eventeumReceipt.getBlockHash())
                    .blockNumber(eventeumReceipt.getBlockNumber())
                    .contractAddress(eventeumReceipt.getContractAddress())
                    .cumulativeGasUsed(eventeumReceipt.getCumulativeGasUsed())
                    .from(eventeumReceipt.getFrom())
                    .gasUsed(eventeumReceipt.getGasUsed())
                    .logs(eventeumReceipt.getLogs())
                    .logsBloom(eventeumReceipt.getLogsBloom())
                    .root(eventeumReceipt.getRoot())
                    .status(eventeumReceipt.getStatus())
                    .to(eventeumReceipt.getTo())
                    .transactionHash(eventeumReceipt.getTransactionHash())
                    .transactionIndex(eventeumReceipt.getTransactionIndex())
                    .build();
        });

    }

    @Override
    public List<InternalTransaction> getInternalTransactions(String txHash) {
        try {
            final List<InternalTransaction> internalTransactions = retryTemplate.execute((context) -> {
                try {
                    return etherscanClient.getInternalTransactions(txHash);
                } catch (RateLimitReachedException e) {
                    Thread.sleep(600);

                    throw new Alert0xException("Rate limit reached");
                }
            });

            log.info("Found {} internal transactions for tx {}", internalTransactions.size(), txHash);
            log.trace("Internal transactions for {}: {}", txHash, JSON.stringify(internalTransactions));

            return internalTransactions;
        } catch (Throwable t) {
            log.error("Unable to obtain internal transactions for tx " + txHash, t);
            return Collections.emptyList();
        }
    }


}
