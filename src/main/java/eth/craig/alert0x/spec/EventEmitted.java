package eth.craig.alert0x.spec;

import eth.craig.alert0x.model.event.ContractEvent;
import eth.craig.alert0x.model.event.TransactionEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventEmitted implements Criterion {

    private static final List<BiFunction<EventEmitted, ContractEvent, Boolean>> verifiers = new ArrayList<>();

    static {
        verifiers.add((criterion, event) -> criterion.getFromContract() == null
                || criterion.getFromContract().equalsIgnoreCase(event.getAddress()));
    }

    private String withSpec;

    private String fromContract;

    @Override
    public boolean matches(Object object) {
        if (!(object instanceof ContractEvent)) {
            return false;
        }

        final ContractEvent contractEvent = (ContractEvent) object;

        return !verifiers
                .stream()
                .anyMatch(verifier -> !verifier.apply(this, contractEvent));
    }
}
