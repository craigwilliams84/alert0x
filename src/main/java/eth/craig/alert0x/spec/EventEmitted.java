package eth.craig.alert0x.spec;

import eth.craig.alert0x.model.event.ContractEvent;
import eth.craig.alert0x.service.BlockchainService;
import eth.craig.alert0x.spec.verifier.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class EventEmitted implements BlockchainInteractingCriterion {

    private final List<CriterionVerifier<EventEmitted, ContractEvent>> verifiers = new ArrayList<>();

    private String spec;

    private String fromContract;

    private List<String> transactionSentFrom;

    private List<String> internalTransactionSentFrom;

    private boolean internalTransactionWithEtherTransfer;

    private List<ArgValue> argValues;

    private BlockchainService blockchainService;

    @Override
    public boolean matches(Object object) {
        if (!(object instanceof ContractEvent)) {
            return false;
        }

        final ContractEvent contractEvent = (ContractEvent) object;

        return !verifiers
                .stream()
                .anyMatch(verifier -> !verifier.isMatching(this, contractEvent));
    }

    @Override
    public void setBlockchainService(BlockchainService blockchainService) {
        this.blockchainService = blockchainService;

        initVerifiers();
    }

    public static EventEmittedBuilder builder() {
        return new EventEmittedBuilder();
    }

    private void initVerifiers() {
        verifiers.add((criterion, event) -> criterion.getFromContract() == null
                || criterion.getFromContract().equalsIgnoreCase(event.getAddress()));

        verifiers.add(new EventTransactionFromVerifier(blockchainService));
        verifiers.add(new EventArgumentValueVerifier());
        verifiers.add(new EventInternalTransactionFromVerifier(blockchainService));
        verifiers.add(new EventInternalTransactionWithEtherValue(blockchainService));
    }

    public static class EventEmittedBuilder {
        private EventEmitted eventEmitted;

        public EventEmittedBuilder() {
            eventEmitted = new EventEmitted();
        }

        public EventEmitted build() {
            return eventEmitted;
        }

        public EventEmittedBuilder withSpec(String spec) {
            eventEmitted.spec = spec;
            return this;
        }

        public EventEmittedBuilder fromContract(String fromContract) {
            eventEmitted.fromContract = fromContract;
            return this;
        }

        public EventEmittedBuilder transactionSentFrom(List<String> transactionSentFrom) {
            if (eventEmitted.transactionSentFrom == null) {
                eventEmitted.transactionSentFrom = new ArrayList<>();
            }

            eventEmitted.transactionSentFrom.addAll(transactionSentFrom);

            return this;
        }

        public EventEmittedBuilder internalTransactionSentFrom(List<String> internalTransactionSentFrom) {
            if (eventEmitted.internalTransactionSentFrom == null) {
                eventEmitted.internalTransactionSentFrom = new ArrayList<>();
            }

            eventEmitted.internalTransactionSentFrom.addAll(internalTransactionSentFrom);

            return this;
        }

        public EventEmittedBuilder withInternalTransactionWithEtherValue() {
            eventEmitted.internalTransactionWithEtherTransfer = true;

            return this;
        }

        public EventEmittedBuilder havingArgumentValue(String value, int argIndex, boolean isIndexed) {
            if (eventEmitted.argValues == null) {
                eventEmitted.argValues = new ArrayList<>();
            }

            eventEmitted.argValues.add(ArgValue
                    .builder()
                    .value(value)
                    .index(argIndex)
                    .isIndexed(isIndexed)
                    .build());

            return this;
        }

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ArgValue {
        private int index;

        private String value;

        private boolean isIndexed;
    }
}
