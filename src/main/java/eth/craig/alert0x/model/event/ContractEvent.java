package eth.craig.alert0x.model.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractEvent implements TransactionBasedEvent {

    private String name;
    private String monitorId;
    private String nodeName;
    private List<EventParameter> indexedParameters;
    private List<EventParameter> nonIndexedParameters;
    private String transactionHash;
    private BigInteger logIndex;
    private BigInteger blockNumber;
    private String blockHash;
    private String address;
    private ContractEventStatus status = ContractEventStatus.UNCONFIRMED;
    private String eventSpecificationSignature;
    private BigInteger timestamp;

}
