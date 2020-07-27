package eth.craig.alert0x.model.monitor;

import eth.craig.alert0x.model.event.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TransactionMonitor {

    private String id;

    private TransactionIdentifierType type;

    private TransactionStatus status;

    private String identifierValue;
}
