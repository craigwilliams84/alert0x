package eth.craig.alert0x.service;

import eth.craig.alert0x.model.ethereum.InternalTransaction;
import eth.craig.alert0x.model.ethereum.TransactionReceipt;

import java.util.List;

public interface BlockchainService {

    TransactionReceipt getTransactionReceipt(String txHash);

    List<InternalTransaction> getInternalTransactions(String txHash);
}
