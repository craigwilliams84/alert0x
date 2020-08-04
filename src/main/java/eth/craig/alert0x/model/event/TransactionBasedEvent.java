package eth.craig.alert0x.model.event;

import java.math.BigInteger;

public interface TransactionBasedEvent {

    String getTransactionHash();
    String getBlockHash();
    BigInteger getBlockNumber();
    BigInteger getTimestamp();
    String getNodeName();
}
