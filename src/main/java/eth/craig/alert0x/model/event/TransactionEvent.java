package eth.craig.alert0x.model.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEvent {

    private String hash;
    private BigInteger nonce;
    private String blockHash;
    private BigInteger blockNumber;
    private BigInteger transactionIndex;
    private String from;
    private String to;
    private BigInteger value;
    private String nodeName;
    private String contractAddress;
    private String data;
    private String revertReason;
    private BigInteger timestamp;
    private TransactionStatus status;

    private String monitorId;
}
