package eth.craig.alert0x.model.ethereum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternalTransaction {

    private String blockNumber;

    private String timeStamp;

    private String from;

    private String to;

    private String value;

    private String contractAddress;

    private String input;

    private String type;

    private String gas;

    private String gasUsed;

    private String isError;

    private String errCode;
}
