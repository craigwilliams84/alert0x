package eth.craig.alert0x.spec;

import eth.craig.alert0x.model.alert.Alert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class AlertSpec {

    private String id;

    private Criterion criterion;

    private List<Alert> alerts;
}
