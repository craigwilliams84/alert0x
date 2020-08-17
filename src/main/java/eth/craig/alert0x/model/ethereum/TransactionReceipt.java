package eth.craig.alert0x.model.ethereum;

import lombok.Builder;
import lombok.Data;
import net.consensys.eventeum.chain.service.domain.Log;

import java.math.BigInteger;
import java.util.List;

@Data
@Builder
public class TransactionReceipt {

    private String transactionHash;
    private BigInteger transactionIndex;
    private String blockHash;
    private BigInteger blockNumber;
    private BigInteger cumulativeGasUsed;
    private BigInteger gasUsed;
    private String contractAddress;
    private String root;
    private String from;
    private String to;
    private List<Log> logs;
    private String logsBloom;
    private String status;
}
