package eth.craig.alert0x.spec;

import eth.craig.alert0x.model.event.TransactionEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionMined implements Criterion {

    private static final List<BiFunction<TransactionMined, TransactionEvent, Boolean>> verifiers = new ArrayList<>();

    static {
        verifiers.add((criterion, tx) -> criterion.getFrom() == null || criterion.getFrom().equalsIgnoreCase(tx.getFrom()));

        verifiers.add((criterion, tx) -> criterion.getTo() == null || criterion.getTo().equalsIgnoreCase(tx.getTo()));

        verifiers.add((criterion, tx) -> criterion.getWithValueGreaterThan() == null
                || tx.getValue().compareTo(criterion.getWithValueGreaterThan()) > 0);

        verifiers.add((criterion, tx) -> criterion.getWithValueLessThan() == null
                || tx.getValue().compareTo(criterion.getWithValueLessThan()) < 0);
    }

    private String from;

    private String to;

    private BigInteger withValueGreaterThan;

    private BigInteger withValueLessThan;

    @Override
    public boolean matches(Object object) {
        if (!(object instanceof TransactionEvent)) {
            return false;
        }

        final TransactionEvent event = (TransactionEvent) object;

        return !verifiers
                .stream()
                .anyMatch(verifier -> !verifier.apply(this, event));
    }
}
