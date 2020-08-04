package eth.craig.alert0x.model.monitor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractEventMonitor {

    private String id;

    private String specificationDefinition;

    private String contractAddress;
}
