package eth.craig.alert0x.service;

import eth.craig.alert0x.model.monitor.ContractEventMonitor;
import eth.craig.alert0x.model.monitor.TransactionMonitor;

public interface BlockchainMonitorService {

    void monitorTransactions(TransactionMonitor transactionMonitor);

    void monitorEvents(ContractEventMonitor contractEventMonitor);
}
